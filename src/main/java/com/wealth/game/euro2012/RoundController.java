package com.wealth.game.euro2012;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.wealth.game.euro2012.data.PlayerMatch;
import com.wealth.game.euro2012.data.PlayerMatchRepository;
import com.wealth.game.euro2012.data.Round;
import com.wealth.game.euro2012.data.RoundRepository;

@Controller
public class RoundController {

	@Autowired private RoundRepository roundRepository;
	@Autowired private PlayerMatchRepository playerMatchRepository;
	
	@RequestMapping(value="/rounds/add", method=RequestMethod.POST)
	@ResponseBody
	public String addRound(@RequestParam String round) {
		Round r = new Round();
		r.setName(round);
		this.roundRepository.save(r);
		
		JsonObject json = new JsonObject();
		json.addProperty("success", true);
		return json.toString();
	}
	
	@RequestMapping(value="/rounds.json", method=RequestMethod.GET)
	@ResponseBody
	public String all() {
		Iterator<Round> it = roundRepository.findAll().iterator();
		List<Round> rounds = new LinkedList<Round>();
		while(it.hasNext()) {
			rounds.add(it.next());
		}
		
		return (new Gson()).toJson(rounds);
	}
	
	@RequestMapping(value="/rounds/active.json", method=RequestMethod.GET)
	@ResponseBody
	public String getActiveRound() {
		Round activeRound = roundRepository.getByActive(true);
		return (new Gson()).toJson(activeRound);
	}
	
	@RequestMapping(value="/rounds/{id}/matches.json", method=RequestMethod.GET)
	@ResponseBody
	public String getRoundPlayerMatches(@PathVariable String id) {
		List<PlayerMatch> matches = this.playerMatchRepository.findByRoundId(id);
		return (new Gson()).toJson(matches);
	}
}
