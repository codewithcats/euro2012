package com.wealth.game.euro2012.data;

import org.springframework.data.repository.CrudRepository;

public interface PlayerMatchRepository extends CrudRepository<PlayerMatch, String>, PlayerMatchRepositoryCustom {
	
	
}
