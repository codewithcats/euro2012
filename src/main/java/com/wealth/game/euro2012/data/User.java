package com.wealth.game.euro2012.data;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class User {

	@Id
	private String id;
	@Indexed
	private String username;
	@Indexed
	private String password;
	@Indexed
	private int leaguePoints;
	private int gamePoints;
	private LeagueStats leagueStats;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public int getLeaguePoints() {
		return leaguePoints;
	}
	public void setLeaguePoints(int leaguePoints) {
		this.leaguePoints = leaguePoints;
	}
	public int getGamePoints() {
		return gamePoints;
	}
	public void setGamePoints(int gamePoints) {
		this.gamePoints = gamePoints;
	}
	public LeagueStats getLeagueStats() {
		if(this.leagueStats == null) this.leagueStats = new LeagueStats();
		return leagueStats;
	}
	public void setLeagueStats(LeagueStats leagueStats) {
		this.leagueStats = leagueStats;
	}
}
