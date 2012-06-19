package com.wealth.game.euro2012;

import java.util.Calendar;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.wealth.game.euro2012.data.LeagueRankingItem;
import com.wealth.game.euro2012.data.LeagueRankingItemRepository;
import com.wealth.game.euro2012.data.LeagueStats;
import com.wealth.game.euro2012.data.Match;
import com.wealth.game.euro2012.data.MatchResult;
import com.wealth.game.euro2012.data.PlayerMatch;
import com.wealth.game.euro2012.data.PlayerMatchRepository;
import com.wealth.game.euro2012.data.PlayerPick;
import com.wealth.game.euro2012.data.User;
import com.wealth.game.euro2012.data.UserRepository;

@Controller
public class PlayerMatchController {
	
	@Autowired private PlayerMatchRepository playerMatchRepository;
	@Autowired private UserRepository userRepository;
	@Autowired private LeagueRankingItemRepository leagueRankingItemRepository;
	
	@RequestMapping(value="/pmatches/pick/fix", method=RequestMethod.POST)
	@ResponseBody
	public String fixPickResult() {
		Iterable<PlayerMatch> matches = this.playerMatchRepository.findAll();
		for(PlayerMatch m: matches) {
			fixPickResult(m.getPlayerOnePick());
			fixPickResult(m.getPlayerTwoPick());
		}
		this.playerMatchRepository.save(matches);
		JsonObject json = new JsonObject();
		json.addProperty("success", false);
		return json.toString();
	}

	private void fixPickResult(PlayerPick playerPick) {
		if(playerPick == null) return;
		String htScore = playerPick.getHtScore();
		String[] ht = htScore.split("-");
		if(Integer.parseInt(ht[0]) > Integer.parseInt(ht[1])) playerPick.setHtResult(MatchResult.WIN);
		else if(Integer.parseInt(ht[0]) == Integer.parseInt(ht[1])) playerPick.setHtResult(MatchResult.DRAW);
		else playerPick.setHtResult(MatchResult.LOSE);
		
		String ftScore = playerPick.getFtScore();
		String[] ft = ftScore.split("-");
		if(Integer.parseInt(ft[0]) > Integer.parseInt(ft[1])) playerPick.setFtResult(MatchResult.WIN);
		else if(Integer.parseInt(ft[0]) == Integer.parseInt(ft[1])) playerPick.setFtResult(MatchResult.DRAW);
		else playerPick.setFtResult(MatchResult.LOSE);
	}

	@RequestMapping(value="/pmatches/pick/save", method=RequestMethod.POST)
	@ResponseBody
	public String pick(@ModelAttribute PlayerPick playerPick, @RequestParam String matchId, HttpSession session) {
		playerPick.setLastUpdate(Calendar.getInstance().getTime());
		PlayerMatch match = this.playerMatchRepository.findOne(matchId);
		User user = (User) session.getAttribute("user");
		if(user.getId().equals(match.getPlayerOne().getId())) {
			match.setPlayerOnePick(playerPick);
		} else {
			match.setPlayerTwoPick(playerPick);
		}
		this.playerMatchRepository.save(match);
		JsonObject json = new JsonObject();
		json.addProperty("success", true);
		json.add("match", (new Gson()).toJsonTree(match));
		return json.toString();
	}
	
	@RequestMapping(value="/pmatches/cal", method=RequestMethod.POST)
	@ResponseBody
	public String calculatePoints(@RequestParam String matchId) {
		List<PlayerMatch> matches = this.playerMatchRepository.findByMatchId(matchId);
		for(PlayerMatch m: matches) {
			Match realMatch = m.getMatch();
			
			PlayerPick playerOnePick = m.getPlayerOnePick();
			int playerOneGamePoint = calculateGamePoints(realMatch, playerOnePick);
			m.setPlayerOnePoints(playerOneGamePoint);
			User playerOne = m.getPlayerOne();
			playerOne.setGamePoints(playerOne.getGamePoints() + playerOneGamePoint);
			
			PlayerPick playerTwoPick = m.getPlayerTwoPick();
			int playerTwoGamePoint = calculateGamePoints(realMatch, playerTwoPick);
			m.setPlayerTwoPoints(playerTwoGamePoint);
			User playerTwo = m.getPlayerTwo();
			playerTwo.setGamePoints(playerTwo.getGamePoints() + playerTwoGamePoint);
			
			LeagueStats playerOneStats = playerOne.getLeagueStats();
			LeagueStats playerTwoStats = playerTwo.getLeagueStats();
			if(playerOneGamePoint > playerTwoGamePoint) {
				playerOne.setLeaguePoints(playerOne.getLeaguePoints() + 3);
				playerOneStats.setWin(playerOneStats.getWin()+1);
				playerTwoStats.setLose(playerTwoStats.getLose()+1);
			} else if(playerTwoGamePoint > playerOneGamePoint) {
				playerTwo.setLeaguePoints(playerTwo.getLeaguePoints() + 3);
				playerTwoStats.setWin(playerTwoStats.getWin()+1);
				playerOneStats.setLose(playerOneStats.getLose()+1);
			} else {
				playerOne.setLeaguePoints(playerOne.getLeaguePoints() + 1);
				playerTwo.setLeaguePoints(playerTwo.getLeaguePoints() + 1);
				playerOneStats.setDraw(playerOneStats.getDraw()+1);
				playerTwoStats.setDraw(playerTwoStats.getDraw()+1);
			}
			playerOneStats.setPlay(playerOneStats.getPlay()+1);
			playerTwoStats.setPlay(playerTwoStats.getPlay()+1);
			
			this.userRepository.save(playerOne);
			this.userRepository.save(playerTwo);
			this.playerMatchRepository.save(m);
			
			processLeagueTable();
		}
		JsonObject json = new JsonObject();
		json.addProperty("success", true);
		return json.toString();
	}

	@RequestMapping(value="/pmatches/league/process", method=RequestMethod.POST)
	@ResponseBody
	public void processLeagueTable() {
		Order leaguePointsOrder = new Order(Direction.DESC, "leaguePoints");
		Order gamePointsOrder = new Order(Direction.DESC, "gamePoints");
		Sort sort = new Sort(leaguePointsOrder, gamePointsOrder);
		Iterator<User> it = this.userRepository.findAll(sort).iterator();
		List<LeagueRankingItem> items = new LinkedList<LeagueRankingItem>();
		int rank = 1;
		while(it.hasNext()) {
			User u = it.next();
			this.leagueRankingItemRepository.deleteAll();
			LeagueRankingItem r = new LeagueRankingItem();
			r.setRank(rank++);
			r.setPlayer(u);
			items.add(r);
		}
		this.leagueRankingItemRepository.save(items);
	}

	private int calculateGamePoints(Match realMatch, PlayerPick playerPick) {
		if(playerPick == null) return -10;
		int point = 0;
		point += calculateResultAndScorePoints(realMatch, playerPick);
		point += calculateBonusPoints(realMatch, playerPick);
		return point;
	}

	private int calculateBonusPoints(Match realMatch, PlayerPick playerPick) {
		int point = 0;
		if(realMatch.getYellowCardMoreThanFive().equals(playerPick.getYellowCardMoreThanFive())) {
			point += 10;
		}
		if(realMatch.getRedCardHappen().equals(playerPick.getRedCardHappen())) {
			if(playerPick.getRedCardHappen().booleanValue()) point += 30;
			else point += 10;
		}
		if(realMatch.getOwnGoalHappen().equals(playerPick.getOwnGoalHappen())) {
			if(playerPick.getOwnGoalHappen().booleanValue()) point += 30;
			else point += 10;
		}
		if(realMatch.getHattrickHappen().equals(playerPick.getHattrickHappen())) {
			if(playerPick.getHattrickHappen().booleanValue()) point += 50;
			else point += 10;
		}
		return point;
	}

	private int calculateResultAndScorePoints(Match realMatch, PlayerPick playerPick) {
		int point = 0;
		point += this.calculateResultAndScorePoints(realMatch.getHtResult(), playerPick.getHtResult(), realMatch.getHtScore(), playerPick.getHtScore());
		point += this.calculateResultAndScorePoints(realMatch.getFtResult(), playerPick.getFtResult(), realMatch.getFtScore(), playerPick.getFtScore());
		return point;
	}
	
	private int calculateResultAndScorePoints(MatchResult result, MatchResult pickResult, String score, String pickScore) {
		int point = 0;
		if(result.equals(pickResult)) {
			point += 20;
			if(score.equals(pickScore)) point += 20;
		}
		return point;
	}
}
