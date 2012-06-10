package com.wealth.game.euro2012;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.JsonObject;
import com.wealth.game.euro2012.data.Match;
import com.wealth.game.euro2012.data.MatchRepository;
import com.wealth.game.euro2012.data.Round;
import com.wealth.game.euro2012.data.RoundRepository;
import com.wealth.game.euro2012.data.Team;
import com.wealth.game.euro2012.data.TeamRepository;

@Controller
@RequestMapping("/matches")
public class MatchController {
	
	@Autowired RoundRepository roundRepository;
	@Autowired TeamRepository teamRepository;
	@Autowired MatchRepository matchRepository;

	@RequestMapping(value="/add", method=RequestMethod.POST)
	@ResponseBody
	public String add(@RequestParam String roundId, @RequestParam String homeId, @RequestParam String awayId) {
		JsonObject json = new JsonObject();
		try {
			Round round = this.roundRepository.findOne(roundId);
			Team home = this.teamRepository.findOne(homeId);
			Team away = this.teamRepository.findOne(awayId);
			Match match = new Match();
			match.setHome(home);
			match.setAway(away);
			this.matchRepository.save(match);
			round.addMatch(match);
			this.roundRepository.save(round);
			json.addProperty("success", true);
		} catch (Exception e) {
			e.printStackTrace();
			json.addProperty("success", false);
		}
		return json.toString();
	}
}
