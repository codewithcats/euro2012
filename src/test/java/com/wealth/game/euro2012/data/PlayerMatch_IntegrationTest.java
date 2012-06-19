package com.wealth.game.euro2012.data;

import java.util.Iterator;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="/IntegrationTest-context.xml")
public class PlayerMatch_IntegrationTest {
	
	@Autowired private PlayerMatchRepository playerMatchRepository;
	
	@Test
	public void list_player_match_order_by_playerOnePick_lastUpdate() {
		Order order = new Order(Direction.DESC, "playerOnePick.lastUpdate");
		Sort lastUpdateSort = new Sort(order);
		Iterator<PlayerMatch> it = this.playerMatchRepository.findAll(lastUpdateSort).iterator();
		while(it.hasNext()) {
			PlayerMatch match = it.next();
			if(match.getPlayerOnePick() != null)
				System.out.println(match.getPlayerOnePick().getLastUpdate());
		}
		
	}

}
