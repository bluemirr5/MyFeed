package com.nnggstory.feedfactory.persistence;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.nnggstory.feedfactory.model.DataUserModel;

@Repository
public class UserDAO {
	@Autowired
	private MongoOperations mongoTemplate;
	
	@Value("${feedfactory.mongodb.collection.user}")private String userCollectionName;  

	@PostConstruct
	public void initCollection() throws Exception {
		if(!mongoTemplate.collectionExists(userCollectionName)){
			mongoTemplate.createCollection(userCollectionName);
		}
	}

	public void saveUser(DataUserModel userDataModel) throws Exception {
		mongoTemplate.save(userDataModel, userCollectionName);
	}
	
	public DataUserModel getUser(String id) throws Exception {
		Query query = new Query();
		query.addCriteria((new Criteria("id")).is(id));
		return mongoTemplate.findOne(query, DataUserModel.class, userCollectionName);
	}
}