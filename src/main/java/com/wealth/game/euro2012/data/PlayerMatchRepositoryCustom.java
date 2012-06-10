package com.wealth.game.euro2012.data;

import java.util.List;

public interface PlayerMatchRepositoryCustom {
	
	List<PlayerMatch> findByRoundId(String roundId);
}
