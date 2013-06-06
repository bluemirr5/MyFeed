package com.nnggstory.feedfactory.model;

import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;

/**
 * 사용자가 피드를 그룹짓기 위한 그룹 모델 클래스.
 * @author bluemirr5
 *
 */
@Document
public class DataFeedGroupModel {
	public String feedGroupId = null;
	public String feedGroupLabel = null;
	public List<String> feedList = null;
}
