package com.wealth.game.euro2012.data;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class PlayerPick {

	@Id
	private String id;
	private MatchResult htResult;
	private MatchResult ftResult;
	private String htScore;
	private String ftScore;
	private Boolean yellowCardMoreThanFive;
	private Boolean redCardHappen;
	private Boolean ownGoalHappen;
	private Boolean hattrickHappen;
	private Date lastUpdate;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public MatchResult getHtResult() {
		return htResult;
	}
	public void setHtResult(MatchResult htResult) {
		this.htResult = htResult;
	}
	public MatchResult getFtResult() {
		return ftResult;
	}
	public void setFtResult(MatchResult ftResult) {
		this.ftResult = ftResult;
	}
	public String getHtScore() {
		return htScore;
	}
	public void setHtScore(String htScore) {
		this.htScore = htScore;
	}
	public String getFtScore() {
		return ftScore;
	}
	public void setFtScore(String ftScore) {
		this.ftScore = ftScore;
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
	public Date getLastUpdate() {
		return lastUpdate;
	}
	public void setLastUpdate(Date lastUpdate) {
		this.lastUpdate = lastUpdate;
	}
}
