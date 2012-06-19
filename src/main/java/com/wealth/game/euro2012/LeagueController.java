package com.wealth.game.euro2012;

import java.util.Calendar;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.wealth.game.euro2012.data.LeagueRankingItem;
import com.wealth.game.euro2012.data.LeagueRankingItemRepository;
import com.wealth.game.euro2012.data.Match;
import com.wealth.game.euro2012.data.PlayerMatch;
import com.wealth.game.euro2012.data.PlayerMatchRepository;
import com.wealth.game.euro2012.data.Round;
import com.wealth.game.euro2012.data.RoundRepository;
import com.wealth.game.euro2012.data.User;
import com.wealth.game.euro2012.data.UserRepository;

@Controller
public class LeagueController {

	@Autowired private RoundRepository roundRepository;
	@Autowired private UserRepository userRepository;
	@Autowired private PlayerMatchRepository playerMatchRepository;
	@Autowired private LeagueRankingItemRepository leagueRankingItemRepository;
	
	@RequestMapping(value="/league/matching", method=RequestMethod.POST)
	@ResponseBody
	public String matchingForRound(@RequestParam String roundId) {
		Round round = this.roundRepository.findOne(roundId);
		List<Match> matches = round.getMatches();
		
		Iterator<User> it = this.userRepository.findAll().iterator();
		List<User> users = new LinkedList<User>();
		while(it.hasNext()) {
			users.add(it.next());
		}
		
		Random rand = new Random(Calendar.getInstance().getTimeInMillis());
		for(Match match: matches) {
			List<User> copy = new LinkedList<User>(users);
			int loopCount = copy.size()/2;
			for(int i=0;i<loopCount;i++) {
				int pickIndex = rand.nextInt(copy.size());
				User playerOne = copy.remove(pickIndex);
				pickIndex = rand.nextInt(copy.size());
				User playerTwo = copy.remove(pickIndex);
				
				PlayerMatch m = new PlayerMatch();
				m.setMatch(match);
				m.setPlayerOne(playerOne);
				m.setPlayerTwo(playerTwo);
				m.setRound(round);
				this.playerMatchRepository.save(m);
			}
		}
		
		JsonObject json = new JsonObject();
		json.addProperty("success", true);
		return json.toString();
	}
	
	@RequestMapping(value="/league/players/{id}/matches.json", method=RequestMethod.GET)
	@ResponseBody
	public String getPlayerMatches(@PathVariable String id) {
		Round activeRound = this.roundRepository.getByActive(true);
		if(activeRound == null) {
			return "[]";
		}
		List<PlayerMatch> matches = this.playerMatchRepository.findByPlayerIdAndActiveRoundId(id, activeRound.getId());
		return (new Gson()).toJson(matches);
	}
	
	@RequestMapping(value="/league/players/history/matches.json", method=RequestMethod.GET)
	@ResponseBody
	public String getPlayerHistoryMatches(HttpSession session) {
		User user = (User) session.getAttribute("user");
		if(user == null) return "[]";
		List<PlayerMatch> matches = this.playerMatchRepository.findPlayerHistoryMatches(user.getId());
		return (new Gson()).toJson(matches);
	}
	
	@RequestMapping(value="/league/table.json", method=RequestMethod.GET)
	@ResponseBody
	public String getTable() {
		Order order = new Order(Direction.ASC, "rank");
		Iterator<LeagueRankingItem> iterator = this.leagueRankingItemRepository.findAll(new Sort(order)).iterator();
		List<LeagueRankingItem> items = new LinkedList<LeagueRankingItem>();
		while(iterator.hasNext()) {
			items.add(iterator.next());
		}
		return (new Gson()).toJson(items);
	}

}
