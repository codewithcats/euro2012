package com.wealth.game.euro2012;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.wealth.game.euro2012.data.User;
import com.wealth.game.euro2012.data.UserRepository;

@Controller
@RequestMapping("/users")
@SessionAttributes("user")
public class UserController {
	
	@Autowired private UserRepository userRepository;
	private User user;

	@RequestMapping(value="/register", method={RequestMethod.POST})
	@ResponseBody
	public String register(@RequestParam String username, @RequestParam String password) {
		JsonObject json = new JsonObject();
		if(isUsernameDuplicate(username)) {
			json.addProperty("success", false);
			json.addProperty("usernameDuplicate", true);
			return json.toString();
		}
		User user = new User();
		user.setUsername(username);
		user.setPassword(password);
		user = this.userRepository.save(user);
		
		this.user = user;
		
		Gson gson = new Gson();
		JsonElement userJson = gson.toJsonTree(user);
		json.addProperty("success", true);
		json.add("user", userJson);
		return json.toString();
	}
	
	private boolean isUsernameDuplicate(String username) {
		User user = this.userRepository.getByUsername(username);
		return user != null;
	}

	@RequestMapping(value="/signin", method={RequestMethod.POST})
	@ResponseBody
	public String signIn(@RequestParam String username, @RequestParam String password) {
		this.user = this.userRepository.getByUsernameAndPassword(username, password);
		JsonObject json = new JsonObject();
		if(this.user == null)
			json.addProperty("success", false);
		else {
			json.addProperty("success", true);
			Gson gson = new Gson();
			json.add("user", gson.toJsonTree(this.user));
		}
		return json.toString();
	}
	
	@RequestMapping(value="/current.json", method={RequestMethod.GET})
	@ResponseBody
	public String current() {
		JsonObject json = new JsonObject();
		if(this.user == null)
			json.addProperty("user", false);
		else {
			Gson gson = new Gson();
			JsonElement userJson = gson.toJsonTree(this.user);
			json.add("user", userJson);
		}
		return json.toString();
	}
	
	@RequestMapping(value="/signout", method={RequestMethod.POST})
	@ResponseBody
	public String signout() {
		this.user = null;
		JsonObject json = new JsonObject();
		json.addProperty("success", true);
		return json.toString();
	}
}
