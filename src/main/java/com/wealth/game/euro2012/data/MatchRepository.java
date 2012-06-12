package com.wealth.game.euro2012.data;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface MatchRepository extends CrudRepository<Match, String> {
	
	List<Match> findByClosedIsFalse();
	List<Match> findByClosedIsTrue();

}
