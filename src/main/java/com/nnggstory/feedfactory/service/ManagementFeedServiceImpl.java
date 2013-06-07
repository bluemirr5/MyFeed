package com.nnggstory.feedfactory.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nnggstory.feedfactory.model.DataUserModel;
import com.nnggstory.feedfactory.model.ViewUserModel;
import com.nnggstory.feedfactory.persistence.UserDAO;

@Service
public class ManagementFeedServiceImpl implements ManagementFeedService {
	@Autowired
	private UserDAO userDAO;

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

	@Override
	public void saveUser(DataUserModel userDataModel) throws Exception {
		userDAO.saveUser(userDataModel);
	}

}
