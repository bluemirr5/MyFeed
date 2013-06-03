package com.nnggstory.rss.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.nnggstory.rss.model.ArticleModel;
import com.nnggstory.rss.model.HostModel;

public interface RssBotService {
	public void gleanArticle();
	public void cleanArticleFromHtml();
	public List<ArticleModel> getArticleList(int pageNum, int pageSize);
	public List<HostModel> getHostList(int pageNum, int pageSize);
	public long getHostTotalSize();
	public boolean insertHost(String hostUrl);
	public void insertHost(MultipartFile rawDataFile) throws Exception;
	public long getArticleTotalSize();
	public List<ArticleModel> getSearchArticleList(String keyword);
}
