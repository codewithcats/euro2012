package com.wealth.game.euro2012.data;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Match {
	@Id
	private String id;
	@DBRef
	private Team home;
	@DBRef
	private Team away;
	
	public String getId() {
		return id;
	}
	public Team getHome() {
		return home;
	}
	public Team getAway() {
		return away;
	}
	public void setId(String id) {
		this.id = id;
	}
	public void setHome(Team home) {
		this.home = home;
	}
	public void setAway(Team away) {
		this.away = away;
	}

}
