package com.wealth.game.euro2012.data;

import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {

	User getByUsername(String username);
	
	User getByUsernameAndPassword(String username, String password);
}
