package com.wealth.game.euro2012.data;

import static org.junit.Assert.*;

import java.util.Calendar;

import org.junit.Test;

public class MatchDay_UnitTest {

	@Test public void testCreateWithDayAndDateTimeAndStage() {
		TournamentStage stage = new TournamentStage("Group Stage");
		Calendar date = Calendar.getInstance();
		date.set(2012, Calendar.JUNE, 8, 23, 0);
		int tournamentDay = 1;
		MatchDay matchDay = new MatchDay(date, tournamentDay, stage);
		assertEquals(date, matchDay.getDate());
		assertEquals(tournamentDay, matchDay.getTournamentDay());
		assertEquals(stage, matchDay.getTournamentStage());
	}
}
