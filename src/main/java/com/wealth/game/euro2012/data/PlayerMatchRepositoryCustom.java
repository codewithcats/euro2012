package com.wealth.game.euro2012.data;

import java.util.List;

public interface PlayerMatchRepositoryCustom {
	
	List<PlayerMatch> findByRoundId(String roundId);
	List<PlayerMatch> findByPlayer(String playerId);
	List<PlayerMatch> findByMatchId(String matchId);
	List<PlayerMatch> findByPlayerIdAndActiveRoundId(String playerId, String roundId);
	List<PlayerMatch> findPlayerHistoryMatches(String playerId);
}
