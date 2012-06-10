package com.wealth.game.euro2012.data;

import org.springframework.data.repository.CrudRepository;

public interface RoundRepository extends CrudRepository<Round, String> {

	Round getByActive(Boolean active);
}
