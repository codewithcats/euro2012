package com.wealth.game.euro2012.data;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"/IntegrationTest-context.xml"})
public class TeamRepository_IntegrationTest {

	@Autowired TeamRepository teamRepository;
	
	@Test public void createTeam() {
		Team expected = new Team("Spain");
		teamRepository.save(expected);
		Team result = teamRepository.findOne(expected.getId());
		assertEquals(expected.getName(), result.getName());
	}
}
