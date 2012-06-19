package com.wealth.game.euro2012.data;

import org.springframework.data.repository.PagingAndSortingRepository;

public interface PlayerMatchRepository extends PagingAndSortingRepository<PlayerMatch, String>, PlayerMatchRepositoryCustom {
	
	
}
