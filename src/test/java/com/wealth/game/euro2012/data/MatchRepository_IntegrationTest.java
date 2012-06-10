package com.wealth.game.euro2012.data;

import java.util.Calendar;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"/IntegrationTest-context.xml"})
public class MatchRepository_IntegrationTest {

	@Autowired MatchRepository matchRepository;
	@Autowired TeamRepository teamRepository;
	
}
