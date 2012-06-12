package com.wealth.game.euro2012;

import java.util.Calendar;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.wealth.game.euro2012.data.PlayerMatch;
import com.wealth.game.euro2012.data.PlayerMatchRepository;
import com.wealth.game.euro2012.data.PlayerPick;
import com.wealth.game.euro2012.data.User;

@Controller
public class PlayerMatchController {
	
	@Autowired private PlayerMatchRepository playerMatchRepository;

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
}
