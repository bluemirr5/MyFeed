package com.nnggstory.feedfactory.service;

import com.nnggstory.feedfactory.model.RssPulishChannelModel;

public interface FeedService {
	public RssPulishChannelModel getRssByUserNGroup(String userId, String groupId) throws Exception;
}
