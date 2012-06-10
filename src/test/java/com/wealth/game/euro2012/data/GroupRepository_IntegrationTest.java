package com.wealth.game.euro2012.data;

import static org.junit.Assert.*;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"/IntegrationTest-context.xml"})
public class GroupRepository_IntegrationTest {

	@Autowired GroupRepository repository;
	
	@Before public void setup() {
		repository.deleteAll();
	}
	
	@Test public void createGroup() {
		Group expected = new Group("A");
		repository.save(expected);
		Group a = repository.findOne(expected.getId());
		assertEquals(expected.getName(), a.getName());
	}
	
	@Test public void addTeamToGroup() {
		Group a = new Group("A");
		repository.save(a);
		a = repository.findOne(a.getId());
		assertEquals(0, a.size());
		Team poland = new Team("Poland");
		a.add(poland);
		repository.save(a);
		a = repository.findOne(a.getId());
		assertEquals(1, a.size());
		Team member1 = a.getMember(0);
		assertEquals(poland.getName(), member1.getName());
	}
	
	@Test public void createMultipleGroup() {
		Group a = new Group("A");
		Group b = new Group("B");
		List<Group> groups = new LinkedList<Group>();
		groups.add(a);
		groups.add(b);
		repository.save(groups);
		Iterator<Group> iterator = repository.findAll().iterator();
		int size = 0;
		while(iterator.hasNext()) {
			size++;
			assertNotNull(iterator.next().getId());
		}
		assertEquals(2, size);
		
	}
	
	@Test public void deleteAll() {
		Group a = new Group("A");
		Group b = new Group("B");
		List<Group> groups = new LinkedList<Group>();
		groups.add(a);
		groups.add(b);
		repository.save(groups);
		repository.deleteAll();
		Iterator<Group> it = repository.findAll().iterator();
		assertFalse(it.hasNext());
	}
}
