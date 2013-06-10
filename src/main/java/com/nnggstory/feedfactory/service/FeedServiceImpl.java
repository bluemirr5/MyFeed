package com.nnggstory.feedfactory.service;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nnggstory.feedfactory.model.ArticleModel;
import com.nnggstory.feedfactory.model.RssPublishItemModel;
import com.nnggstory.feedfactory.model.RssPulishChannelModel;

@Service
public class FeedServiceImpl implements FeedService {
	@Autowired
	private RssRequestManager rssRequestManager;
	
	@Autowired
	private ManagementFeedService managementFeedService;
	
	@Override
	public RssPulishChannelModel getRssByUserNGroup(String userId, String groupId) throws Exception {
		RssPulishChannelModel rssPublicModel = null;
		List<String> feedHostList = managementFeedService.getFeedHostListByUserNFeedGroup(userId, groupId);
		
		if(feedHostList != null && feedHostList.size() > 0) {
			List<RssPublishItemModel> totalArticleList = new ArrayList<RssPublishItemModel>();
			rssPublicModel = new RssPulishChannelModel();
			for(String hostUrl : feedHostList) {
				//TODO Thread 처리 가능하다면 추후 변경
				InputStream is = rssRequestManager.getRequest(hostUrl);
				RssArticleParser rssParser = new RssArticleParser();
				rssParser.parser(is);
				List<ArticleModel> articleList = rssParser.getArticleList();
				is.close();
				if(articleList != null && articleList.size() > 0) {
					totalArticleList.addAll(articleList);
				}
			}
			//TODO sort
			
			rssPublicModel.setItem(totalArticleList);
		}
		
		return rssPublicModel;
	}
}
