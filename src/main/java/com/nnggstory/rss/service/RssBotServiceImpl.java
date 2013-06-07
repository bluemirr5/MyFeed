package com.nnggstory.rss.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.nnggstory.feedfactory.service.RssRequestManager;
import com.nnggstory.rss.dao.RssDao;
import com.nnggstory.rss.model.ArticleModel;
import com.nnggstory.rss.model.HostModel;
import com.nnggstory.rss.service.actionobj.HTMLCleaner;
import com.nnggstory.rss.service.actionobj.RssArticleParser;
import com.nnggstory.rss.service.actionobj.RssHostParser;

@Service
public class RssBotServiceImpl implements RssBotService {
	@Autowired
	private RssDao rssDao;
	
//	@Scheduled(fixedRate=3600000)
	@Override
	public void gleanArticle() {
		HTMLCleaner cleaner = new HTMLCleaner();
		List<ArticleModel> articleList = new ArrayList<ArticleModel>();
		RssRequestManager rrm = new RssRequestManager();
		InputStream is = null;
		List<HostModel> hostList = rssDao.getHostAllList();
		for(int i = 0; i < hostList.size(); i++) {
			HostModel hostModel = hostList.get(i);
			
			try {
				is = rrm.getRequest(hostModel.getHostUrl());
				RssArticleParser parser = new RssArticleParser();
				parser.parser(is);
				List<ArticleModel> localArticleList = parser.getArticleList(hostModel.getHostUrl());
				articleList.addAll(localArticleList);
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if(is != null) {
					try {
						is.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
		
		// html Clean
		for (int j = 0; j < articleList.size(); j++) {
			ArticleModel article = articleList.get(j);
			String cleanText = cleaner.clean(article.getDescription());
			article.setText(cleanText);
			System.out.println(article.getTitle());
		}
		
		// duplication Clean
		cleanDuplication(articleList);
		
		System.out.println(articleList.size());
		for (int i = 0; i < articleList.size(); i++) {
			rssDao.insertArticle(articleList.get(i));
		}
	}
	
	@Override
	public void cleanArticleFromHtml() {
		HTMLCleaner cleaner = new HTMLCleaner();
		List<ArticleModel> articleList = rssDao.getArticleAllList();
		for (int i = 0; i < articleList.size(); i++) {
			ArticleModel articleModel = articleList.get(i);
			String cleanText = cleaner.clean(articleModel.getDescription());
			articleModel.setText(cleanText);
			rssDao.insertArticle(articleModel);
		}
	}

	@Override
	public List<ArticleModel> getArticleList(int pageNum, int pageSize) {
		return rssDao.getArticleList(pageNum, pageSize);
	}
	
	@Override
	public List<HostModel> getHostList(int pageNum, int pageSize) {
		return rssDao.getHostList(pageNum, pageSize);
		
	}
	
	@Override
	public long getHostTotalSize() {
		return rssDao.getHostTotalSize();
		
	}
	
	@Override
	public long getArticleTotalSize() {
		return rssDao.getArticleTotalSize();
	}

	@Override
	public void insertHost(MultipartFile rawDataFile) throws Exception {
		String fileName = rawDataFile.getOriginalFilename();
		if(fileName == null) {
			throw new Exception();
		}
		String[] fileNameArray = fileName.split("\\.");
		String ext = fileNameArray[fileNameArray.length - 1];
		InputStream is = rawDataFile.getInputStream();
		Workbook workBook = null;

		// 
		if("xlsx".equalsIgnoreCase(ext)) {
			workBook = new XSSFWorkbook(is);
		} else if("xls".equalsIgnoreCase(ext)) {
			workBook = new HSSFWorkbook(is);
		} else {
			// 
			throw new Exception();
		}
		
		Sheet sheet = workBook.getSheetAt(0);
		int lastRowNum = sheet.getLastRowNum();
		
		for (int i = 0; i < lastRowNum; i++) {
			Row row  = sheet.getRow(i);
			String hostUrl = "";
			if(row != null) {
				Cell cell = row.getCell(0);
				if(cell != null) {
					hostUrl = cell.getStringCellValue();
					insertHost(hostUrl);
				}
			}
		}
	}
	
	@Override
	public List<ArticleModel> getSearchArticleList(String keyword) {
		return rssDao.getArticleList(keyword);
	}
	
	@Override
	public boolean insertHost(String hostUrl) {
		boolean ret = true;
		RssRequestManager rrm = new RssRequestManager();
		InputStream is = null;
		try {
			is = rrm.getRequest(hostUrl);
			RssHostParser parser = new RssHostParser();
			parser.parser(is);
			HostModel hostModel = parser.getHostModel(hostUrl);
			rssDao.insertHost(hostModel);
			is.close();
		} catch (Exception e) {
			e.printStackTrace();
			ret = false;
		} finally {
			if(is != null) {
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return ret;
	}
	
	private void cleanDuplication(List<ArticleModel> articleList) {
		// Dup Clean
		if(articleList.size() > 2) {
			List<String> articleTextList = new ArrayList<String>();
			for(int i = 0; i < articleList.size(); i++) {
				ArticleModel model = articleList.get(i);
				articleTextList.add(model.getText());
			}
			
			List<String> dupStrings = dupCheckString(articleTextList);
			for(int j = dupStrings.size() - 1; j >= 0 ; j--) {
				String dupString = dupStrings.get(j);
				for (int i = 0; i < articleList.size(); i++) {
					ArticleModel model = articleList.get(i);
					if(!model.getText().contains(dupString)) {
						dupStrings.remove(j);
						break;
					}
				}
			}
			for(int j = dupStrings.size() - 1; j >= 0 ; j--) {
				String dupString = dupStrings.get(j);
				System.out.println(dupString);
				for (int i = 0; i < articleList.size(); i++) {
					ArticleModel model = articleList.get(i);
					model.setText(model.getText().replaceAll(dupString, ""));
				}
			}
		}
	}
	
	private List<String> dupCheckString(List<String> docList) {
		final int minDupLength = 20;
		List<String> dupList = new ArrayList<String>();
		Map<Integer, String> dupStringMap = new HashMap<Integer, String>();
		
		if(docList.size() < 2) {
			return dupList;
		}
		
		String doc1 = docList.get(0);
		String doc2 = docList.get(1);
		for(int startPoint = 0; startPoint < doc1.length(); startPoint++) {
			int endPoint = startPoint + minDupLength;
			if(doc1.length() <= endPoint) {
				break;
			}
			String curString = doc1.substring(startPoint, endPoint+1);
			if(doc2.contains(curString)) {
				dupStringMap.put(startPoint, curString);
			}
		}
		
		
		for(int i = 0; i < docList.size(); i++) {
			String doc = docList.get(i);
			Iterator<Integer> iterator = dupStringMap.keySet().iterator();
			List<Integer> removeKey = new ArrayList<Integer>();
			while(iterator.hasNext()) {
				Integer key = iterator.next();
				String dupString = dupStringMap.get(key);
				if(!doc.contains(dupString)) {
					removeKey.add(key);
				}
			}
			for(int j = 0; j < removeKey.size(); j++) {
				dupStringMap.remove(removeKey.get(j));
			}
		}
		
		for(int i = doc1.length() - 1; i >= 0 ; i--) {
			String curDupString = dupStringMap.get(i);
			if(curDupString != null && i - 1 >= 0) {
				String preDupStringTemp = dupStringMap.get(i - 1);
				if(preDupStringTemp != null) {
					String addString = preDupStringTemp.substring(0, 1);
					curDupString = addString + curDupString;
					dupStringMap.put(i - 1, curDupString);
					dupStringMap.remove(i);
				}
			}
		}
		
		Iterator<Integer> iter = dupStringMap.keySet().iterator();
		while(iter.hasNext()) {
			Integer key = iter.next();
			String dupString = dupStringMap.get(key);
			String[] dupStringArray = dupString.split("\\n");
			dupList.addAll(Arrays.asList(dupStringArray));
//			if(dupString.contains("\\n")) {
//				
//			} else {
//				dupList.add(dupString);
//			}
		}
		
		return dupList;
	}
}
