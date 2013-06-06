package com.nnggstory.feedfactory.service;

import com.nnggstory.feedfactory.model.DataUserModel;
import com.nnggstory.feedfactory.model.UserViewModel;

public interface ManagementFeedService {
	/**
	 * 데이터 베이스에서 사용자의 정보를 가져와 반환한다.
	 * 
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	public UserViewModel getUser(String userId) throws Exception;
	/**
	 * 데이터 베이스에 사용자 정보를 저장한다.
	 * 
	 * @param userDataModel
	 * @throws Exception
	 */
	public void saveUser(DataUserModel userDataModel) throws Exception;
}