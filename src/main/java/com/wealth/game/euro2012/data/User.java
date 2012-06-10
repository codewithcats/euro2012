package com.wealth.game.euro2012.data;

import java.math.BigInteger;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class User {

	@Id
	private BigInteger id;
	@Indexed
	private String username;
	@Indexed
	private String password;
	@Indexed
	private int leaguePoints;
	
	public BigInteger getId() {
		return id;
	}
	public void setId(BigInteger id) {
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
}
