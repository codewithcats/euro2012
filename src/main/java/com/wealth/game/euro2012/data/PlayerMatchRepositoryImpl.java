package com.wealth.game.euro2012.data;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;

import static org.springframework.data.mongodb.core.query.Query.query;
import static org.springframework.data.mongodb.core.query.Criteria.where;

public class PlayerMatchRepositoryImpl implements PlayerMatchRepositoryCustom {

	@Autowired private MongoOperations mongoOperations;
	
	public List<PlayerMatch> findByRoundId(String roundId) {
		return this.mongoOperations.find(query(where("round.$id").is(new ObjectId(roundId))), PlayerMatch.class);
	}

}
