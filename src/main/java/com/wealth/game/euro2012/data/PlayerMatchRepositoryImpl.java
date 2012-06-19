package com.wealth.game.euro2012.data;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;

public class PlayerMatchRepositoryImpl implements PlayerMatchRepositoryCustom {

	@Autowired private MongoOperations mongoOperations;
	
	public List<PlayerMatch> findByRoundId(String roundId) {
		return this.mongoOperations.find(query(where("round.$id").is(new ObjectId(roundId))), PlayerMatch.class);
	}

	public List<PlayerMatch> findByPlayer(String playerId) {
		ObjectId id = new ObjectId(playerId);
		Criteria criteria = new Criteria();
		criteria.orOperator(where("playerOne.$id").is(id), where("playerTwo.$id").is(id));
		return this.mongoOperations.find(query(criteria), PlayerMatch.class);
	}

	public List<PlayerMatch> findByMatchId(String matchId) {
		ObjectId id = new ObjectId(matchId);
		Criteria criteria = where("match.$id").is(id);
		return this.mongoOperations.find(query(criteria), PlayerMatch.class);
	}

	public List<PlayerMatch> findByPlayerIdAndActiveRoundId(String playerId, String roundId) {
		ObjectId rid = new ObjectId(roundId);
		ObjectId pid = new ObjectId(playerId);
		Criteria playerCriteria = (new Criteria()).orOperator(where("playerOne.$id").is(pid), where("playerTwo.$id").is(pid));
		Criteria criteria = where("round.$id").is(rid).andOperator(playerCriteria);
		return this.mongoOperations.find(query(criteria), PlayerMatch.class);
	}

	public List<PlayerMatch> findPlayerHistoryMatches(String playerId) {
		Round activeRound = this.mongoOperations.findOne(query(where("active").is(true)), Round.class);
		ObjectId pid = new ObjectId(playerId);
		Criteria playerCriteria = (new Criteria()).orOperator(where("playerOne.$id").is(pid), where("playerTwo.$id").is(pid));
		if(activeRound == null) return this.mongoOperations.find(query(playerCriteria), PlayerMatch.class);
		Criteria criteria = where("round.$id").ne(new ObjectId(activeRound.getId())).andOperator(playerCriteria);
		return this.mongoOperations.find(query(criteria), PlayerMatch.class);
	}

}
