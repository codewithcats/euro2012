package com.wealth.game.euro2012.data;

import java.util.ArrayList;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.wealth.game.euro2012.data.exception.OverCapacityGroupException;

@Document
public class Group {
	@Id
	private ObjectId id;
	private String name;
	private List<Team> members = new ArrayList<Team>();
	private boolean save;

	public Group(String name) {
		setName(name);
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return this.name;
	}

	public ObjectId getId() {
		return this.id;
	}

	public void add(Team team) {
		if(this.members.size()==4)
			throw new OverCapacityGroupException();
		this.members.add(team);
	}

	public int size() {
		return this.members.size();
	}

	public boolean contains(Team team) {
		return this.members.contains(team);
	}

	public Team getMember(int i) {
		return this.members.get(i);
	}

	public boolean isSave() {
		return save;
	}

	public void setSave(boolean save) {
		this.save = save;
	}

}
