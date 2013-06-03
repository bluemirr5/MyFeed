package com.nnggstory.feedfactory.persistence;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;

@Repository
public class UserDAO {
  @Autowired
	private MongoOperations mongoTemplate;
 
 @PostConstruct
	public void initCollection() throws Exception {
		if(!mongoTemplate.collectionExists("user")){
			mongoTemplate.createCollection("user");
		}
	}
 
 
}