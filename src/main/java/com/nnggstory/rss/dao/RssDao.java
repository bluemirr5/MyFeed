package com.nnggstory.rss.dao;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.nnggstory.rss.model.ArticleModel;
import com.nnggstory.rss.model.HostModel;

@Repository
public class RssDao {
	@Autowired
	private MongoOperations mongoTemplate;
	
	@PostConstruct
	public void initCollection() throws Exception {
		if(!mongoTemplate.collectionExists("article")){
			mongoTemplate.createCollection("article");
		}
		if(!mongoTemplate.collectionExists("host")){
			mongoTemplate.createCollection("host");
		}
	}
	
	public void insertHost(HostModel hostModel) {
		mongoTemplate.save(hostModel, "host");
	}
	
	public List<HostModel> getHostList(int pageNum, int pageSize) {
		Query query = new Query();
		query.skip((pageNum-1)*pageSize).limit(pageSize);
		return mongoTemplate.find(query, HostModel.class, "host");
	}
	
	public List<HostModel> getHostAllList() {
		Query query = new Query();
		return mongoTemplate.find(query, HostModel.class, "host");
		
	}
	public long getHostTotalSize() {
		Query query = new Query();
		return mongoTemplate.count(query, "host");
	}
	
	public long getArticleTotalSize() {
		Query query = new Query();
		return mongoTemplate.count(query, "article");
	}
	
	public void insertArticle(ArticleModel article) {
		mongoTemplate.save(article, "article");
	}
	
	public void insertArticleList(List<ArticleModel> articleList) {
		mongoTemplate.insert(articleList, "article");
	}
	
	
	public List<ArticleModel> getArticleAllList() {
		Query query = new Query();
		return mongoTemplate.find(query, ArticleModel.class, "article");
		
	}
	public List<ArticleModel> getArticleList(int pageNum, int pageSize) {
		Query query = new Query();
		query.skip((pageNum-1)*pageSize).limit(pageSize);
		return mongoTemplate.find(query, ArticleModel.class, "article");
	}
	
	public List<ArticleModel> getArticleList(String keyword) {
		//title
		Criteria criteriaTitle = Criteria.where("title");
		criteriaTitle.regex(keyword);
		//desc
		Criteria criteriaDesc = Criteria.where("text");
		criteriaDesc.regex(keyword);
		
		// or
		Criteria criteriaPrefix = new Criteria();
		criteriaPrefix.orOperator(criteriaTitle, criteriaDesc);
		
		Query query = Query.query(criteriaPrefix);
		return mongoTemplate.find(query, ArticleModel.class, "article");
	}
	
	public List<ArticleModel> getArticleListByHost(String host) {
		Criteria criteriaHost = Criteria.where("host");
		criteriaHost.is(host);
		Query query = Query.query(criteriaHost);
		return mongoTemplate.find(query, ArticleModel.class, "article");
	}
}
