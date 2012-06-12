package com.wealth.game.euro2012;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.wealth.game.euro2012.data.Match;
import com.wealth.game.euro2012.data.MatchRepository;
import com.wealth.game.euro2012.data.Round;
import com.wealth.game.euro2012.data.RoundRepository;
import com.wealth.game.euro2012.data.Team;
import com.wealth.game.euro2012.data.TeamRepository;

@Controller
public class MatchController {
	
	@Autowired RoundRepository roundRepository;
	@Autowired TeamRepository teamRepository;
	@Autowired MatchRepository matchRepository;

	@RequestMapping(value="/matches/add", method=RequestMethod.POST)
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
	
	@RequestMapping(value="/matches/open.json", method=RequestMethod.GET)
	@ResponseBody
	public String openMatchesList() {
		return (new Gson()).toJson(matchRepository.findByClosedIsFalse());
	}
	
	@RequestMapping(value="/matches/close.json", method=RequestMethod.GET)
	@ResponseBody
	public String closeMatchesList() {
		return (new Gson()).toJson(matchRepository.findByClosedIsTrue());
	}
	
	@RequestMapping(value="/matches/{id}/close", method=RequestMethod.POST)
	@ResponseBody
	public String closeMatch(@ModelAttribute Match match, @PathVariable String id) {
		Match saved;
		try {
			Match readed = this.matchRepository.findOne(id);
			match.setHome(readed.getHome());
			match.setAway(readed.getAway());
			match.setClosed(true);
			saved = this.matchRepository.save(match);
			return (new Gson()).toJson(saved);
		} catch (Exception e) {
			e.printStackTrace();
		}
		JsonObject json = new JsonObject();
		json.addProperty("failed", true);
		return json.toString();
	}
}
