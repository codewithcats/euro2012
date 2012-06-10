package com.wealth.game.euro2012;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.wealth.game.euro2012.data.Group;
import com.wealth.game.euro2012.data.GroupRepository;
import com.wealth.game.euro2012.data.Team;

@Controller
public class GroupController {
	
	@Autowired GroupRepository groupRepository;
	
	@RequestMapping(value="/groups.json")
	@ResponseBody
	public String all() {
		Iterator<Group> it = groupRepository.findAll().iterator();
		List<Group> groups = new LinkedList<Group>();
		while(it.hasNext()) groups.add(it.next());
		Gson gson = new Gson();
		JsonElement e = gson.toJsonTree(groups);
		JsonObject json = new JsonObject();
		json.add("groups", e);
		return json.toString();
	}
	
	@RequestMapping(value="/groups/reset")
	@ResponseBody
	public String reset() {
		groupRepository.deleteAll();
		List<Group> groups = new LinkedList<Group>();
		Group a = new Group("A");
		a.add(new Team("Poland"));
		a.add(new Team("Greece"));
		a.add(new Team("Russia"));
		a.add(new Team("Chezch Republic"));
		groups.add(a);
		Group b = new Group("B");
		b.add(new Team("Netherland"));
		b.add(new Team("Denmark"));
		b.add(new Team("Germany"));
		b.add(new Team("Portugal"));
		groups.add(b);
		Group c = new Group("C");
		groups.add(c);
		c.add(new Team("Spain"));
		c.add(new Team("Italy"));
		c.add(new Team("Ireland"));
		c.add(new Team("Croatia"));
		Group d = new Group("D");
		d.add(new Team("Ukraine"));
		d.add(new Team("Sweden"));
		d.add(new Team("France"));
		d.add(new Team("England"));
		groups.add(d);
		groupRepository.save(groups);
		JsonObject json = new JsonObject();
		json.addProperty("success", true);
		return json.toString();
	}
}
