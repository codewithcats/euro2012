package com.wealth.game.euro2012.data;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

public class LeagueRankingItemRepositoryImpl implements LeagueRankingItemRepositoryCustom {
	
	@Autowired private MongoOperations mongoOperations;

	public LeagueRankingItem getByUserId(String playerId) {
		ObjectId id = new ObjectId(playerId);
		Criteria criteria = where("user.$id").is(id);
		return this.mongoOperations.findOne(query(criteria), LeagueRankingItem.class);
	}

}
