package com.wealth.game.euro2012.data;

import static org.junit.Assert.*;

import org.junit.Test;

public class Team_UnitTest {

	@Test public void testCreateWithName() {
		String name = "Spain";
		Team spain = new Team(name);
		assertEquals(name, spain.getName());
	}
}
