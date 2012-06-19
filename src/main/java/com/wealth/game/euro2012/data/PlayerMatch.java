package com.wealth.game.euro2012.data;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class PlayerMatch {

	@Id
	private String id;
	@DBRef
	private User playerOne;
	@DBRef
	private User playerTwo;
	@DBRef
	private Match match;
	@DBRef
	private Round round;
	
	private PlayerPick playerOnePick;
	private int playerOnePoints;
	private PlayerPick playerTwoPick;
	private int playerTwoPoints;
	
	public String getId() {
		return id;
	}
	public User getPlayerOne() {
		return playerOne;
	}
	public User getPlayerTwo() {
		return playerTwo;
	}
	public Match getMatch() {
		return match;
	}
	public void setId(String id) {
		this.id = id;
	}
	public void setPlayerOne(User playerOne) {
		this.playerOne = playerOne;
	}
	public void setPlayerTwo(User playerTwo) {
		this.playerTwo = playerTwo;
	}
	public void setMatch(Match match) {
		this.match = match;
	}
	public Round getRound() {
		return round;
	}
	public void setRound(Round round) {
		this.round = round;
	}
	public PlayerPick getPlayerOnePick() {
		return playerOnePick;
	}
	public void setPlayerOnePick(PlayerPick playerOnePick) {
		this.playerOnePick = playerOnePick;
	}
	public PlayerPick getPlayerTwoPick() {
		return playerTwoPick;
	}
	public void setPlayerTwoPick(PlayerPick playerTwoPick) {
		this.playerTwoPick = playerTwoPick;
	}
	public int getPlayerOnePoints() {
		return playerOnePoints;
	}
	public void setPlayerOnePoints(int playerOnePoints) {
		this.playerOnePoints = playerOnePoints;
	}
	public int getPlayerTwoPoints() {
		return playerTwoPoints;
	}
	public void setPlayerTwoPoints(int playerTwoPoints) {
		this.playerTwoPoints = playerTwoPoints;
	}
}
