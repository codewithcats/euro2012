package com.wealth.game.euro2012.data;

import org.springframework.data.repository.PagingAndSortingRepository;

public interface UserRepository extends PagingAndSortingRepository<User, String> {

	User getByUsername(String username);
	
	User getByUsernameAndPassword(String username, String password);
	
}
