package com.wealth.game.euro2012;

import java.util.Calendar;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.JsonObject;
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
			for(int i=0;i<loopCount;i+=2) {
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
}
