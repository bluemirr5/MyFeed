package com.nnggstory.feedfactory.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nnggstory.feedfactory.model.DataFeedGroupModel;
import com.nnggstory.feedfactory.model.DataUserModel;
import com.nnggstory.feedfactory.model.ViewUserModel;
import com.nnggstory.feedfactory.persistence.UserDAO;

@Service
public class ManagementFeedServiceImpl implements ManagementFeedService {
	@Autowired
	private UserDAO userDAO;
	
	/**
	 * 데이터 베이스에서 사용자의 정보를 가져와 반환한다.
	 * 
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	@Override
	public ViewUserModel getUser(String userId) throws Exception {
		DataUserModel dataModel = userDAO.getUser(userId);
		ViewUserModel viewModel = null;
		if(dataModel != null) {
			viewModel = new ViewUserModel();
			viewModel.matchToViewModel(dataModel);
		}
		return viewModel;
	}

	/**
	 * 데이터 베이스에 사용자 정보를 저장한다.
	 * 
	 * @param userDataModel
	 * @throws Exception
	 */
	@Override
	public void saveUser(DataUserModel userDataModel) throws Exception {
		userDAO.saveUser(userDataModel);
	}
	
	/**
	 * 특정 사용자, 특정 feed 그룹의 feedHostList를 반환한다.
	 * 
	 * @param userId
	 * @param groupId
	 * @return
	 * @throws Exception
	 */
	@Override
	public List<String> getFeedHostListByUserNFeedGroup(String userId, String groupId) throws Exception {
		DataUserModel dataModel = userDAO.getUser(userId);
		List<DataFeedGroupModel> feedGroupList = dataModel.feedGroupList;
		List<String> feedHostUrlList = null;
		for(DataFeedGroupModel feedGroupModel : feedGroupList) {
			if(feedGroupModel.feedGroupId == groupId) {
				feedHostUrlList = feedGroupModel.feedList;
				break;
			}
		}
		return feedHostUrlList;
	}

}
