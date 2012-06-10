package com.wealth.game.euro2012.data;

import java.util.Calendar;

public class MatchDay {

	private Calendar calendar;
	private int tournamentDay;
	private TournamentStage tournamentStage;

	public MatchDay(Calendar date, int tournamentDay, TournamentStage stage) {
		setCalendar(date);
		setTournamentDay(tournamentDay);
		setTournamentStage(stage);
	}

	public void setTournamentStage(TournamentStage stage) {
		this.tournamentStage = stage;
	}

	public void setTournamentDay(int tournamentDay) {
		this.tournamentDay = tournamentDay;
	}

	public void setCalendar(Calendar date) {
		this.calendar = date;
	}

	public Calendar getDate() {
		return this.calendar;
	}

	public int getTournamentDay() {
		return this.tournamentDay;
	}

	public TournamentStage getTournamentStage() {
		return this.tournamentStage;
	}

}
