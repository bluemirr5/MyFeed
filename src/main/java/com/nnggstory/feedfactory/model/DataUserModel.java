package com.nnggstory.feedfactory.model;

import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;

/**
 * 사용자 전체 모델 클래스.
 * 
 * @author bluemirr5
 *
 */
@Document
public class DataUserModel {
	public String id = null;
	public String password = null;
	public String email = null;
	public String nickName = null;
	public List<DataFeedGroupModel> feedGroupList = null;
}