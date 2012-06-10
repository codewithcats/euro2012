package com.wealth.game.euro2012.data;

import java.util.Calendar;

import org.junit.Test;

public class Match_UnitTest {

	@Test public void testCreate() {
		TournamentStage stage = new TournamentStage("Group Stage");
		Calendar date = Calendar.getInstance();
		date.set(2012, Calendar.JUNE, 8, 23, 0);
		MatchDay mDay = new MatchDay(date, 1, stage);
	}
}
