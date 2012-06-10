package com.wealth.game.euro2012;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.wealth.game.euro2012.data.Team;
import com.wealth.game.euro2012.data.TeamRepository;

@Controller
public class TeamController {

	@Autowired private TeamRepository teamRepository;
	
	@RequestMapping(value="/teams.json", method=RequestMethod.GET)
	@ResponseBody
	public String list() {
		Iterator<Team> it = this.teamRepository.findAll().iterator();
		List<Team> teams = new LinkedList<Team>();
		while(it.hasNext()) teams.add(it.next());
		return (new Gson()).toJson(teams);
	}
	
	@RequestMapping(value="/teams/add", method=RequestMethod.POST)
	@ResponseBody
	public String addTeam(@RequestParam String team) {
		Team t = new Team(team);
		this.teamRepository.save(t);
		
		JsonObject json = new JsonObject();
		json.addProperty("success", true);
		return json.toString();
	}
}
