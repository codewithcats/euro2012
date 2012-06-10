package com.wealth.game.euro2012.data;

import org.springframework.data.repository.CrudRepository;

public interface TeamRepository extends CrudRepository<Team, String> {

	Team findByName(String name);
}
