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
	
	private Integer htHomeScore;
	private Integer ftHomeScore;
	private Integer htAwayScore;
	private Integer ftAwayScore;
	private Boolean yellowCardMoreThanFive;
	private Boolean redCardHappen;
	private Boolean ownGoalHappen;
	private Boolean hattrickHappen;
	private Boolean closed = false;
	
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
	public Integer getHtHomeScore() {
		return htHomeScore;
	}
	public void setHtHomeScore(Integer htHomeScore) {
		this.htHomeScore = htHomeScore;
	}
	public Integer getFtHomeScore() {
		return ftHomeScore;
	}
	public void setFtHomeScore(Integer ftHomeScore) {
		this.ftHomeScore = ftHomeScore;
	}
	public Integer getHtAwayScore() {
		return htAwayScore;
	}
	public void setHtAwayScore(Integer htAwayScore) {
		this.htAwayScore = htAwayScore;
	}
	public Integer getFtAwayScore() {
		return ftAwayScore;
	}
	public void setFtAwayScore(Integer ftAwayScore) {
		this.ftAwayScore = ftAwayScore;
	}
	public Boolean getOwnGoalHappen() {
		return ownGoalHappen;
	}
	public void setOwnGoalHappen(Boolean ownGoalHappen) {
		this.ownGoalHappen = ownGoalHappen;
	}
	public Boolean getHattrickHappen() {
		return hattrickHappen;
	}
	public void setHattrickHappen(Boolean hattrickHappen) {
		this.hattrickHappen = hattrickHappen;
	}
	public Boolean getClosed() {
		return closed;
	}
	public void setClosed(Boolean closed) {
		this.closed = closed;
	}
	public Boolean getYellowCardMoreThanFive() {
		return yellowCardMoreThanFive;
	}
	public void setYellowCardMoreThanFive(Boolean yellowCardMoreThanFive) {
		this.yellowCardMoreThanFive = yellowCardMoreThanFive;
	}
	public Boolean getRedCardHappen() {
		return redCardHappen;
	}
	public void setRedCardHappen(Boolean redCardHappen) {
		this.redCardHappen = redCardHappen;
	}
	
}
