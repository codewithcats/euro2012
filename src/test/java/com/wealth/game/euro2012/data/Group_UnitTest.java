package com.wealth.game.euro2012.data;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.wealth.game.euro2012.data.exception.OverCapacityGroupException;

public class Group_UnitTest {
	
	private static final String TEAM_POLAND_NAME = "Poland";
	private static final String GROUP_A_NAME = "A";
	private Group a;
	private Team poland;
	
	@Before public void setup() {
		a = new Group(GROUP_A_NAME);
		poland = new Team(TEAM_POLAND_NAME);
	}

	@Test public void testCreateWithName() {
		assertEquals(GROUP_A_NAME, a.getName());
	}
	
	@Test public void testAddTeam() {
		a.add(poland);
		assertEquals(1, a.size());
		assertTrue(a.contains(poland));
	}
	
	@Test(expected=OverCapacityGroupException.class)
	public void testAddMoreThanFourTeams() {
		Team greece = new Team("Greece");
		Team russia = new Team("Russia");
		Team chezch = new Team("Chezch");
		Team spain = new Team("Spain");
		a.add(greece);
		a.add(russia);
		a.add(chezch);
		a.add(poland);
		a.add(spain);
	}
}
