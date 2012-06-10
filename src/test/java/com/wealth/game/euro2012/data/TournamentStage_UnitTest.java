package com.wealth.game.euro2012.data;

import static org.junit.Assert.*;

import org.junit.Test;

public class TournamentStage_UnitTest {

	@Test public void testCreateWithName() {
		TournamentStage stage = new TournamentStage("name");
		assertEquals("name", stage.getName());
	}
}
