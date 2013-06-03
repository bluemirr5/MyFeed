package com.nnggstory.rss.service;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.snu.ids.ha.ma.MExpression;
import org.snu.ids.ha.ma.Morpheme;
import org.snu.ids.ha.ma.MorphemeAnalyzer;
import org.snu.ids.ha.ma.Sentence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nnggstory.rss.dao.RssDao;
import com.nnggstory.rss.model.ArticleModel;
import com.nnggstory.rss.model.HostModel;

@Service
public class RssAnalyzeServiceImpl implements RssAnalyzeService {
	private final static int TITLE_POINT = 5;
	private final static int TEXT_POINT = 1;
	
	@Autowired
	private RssDao rssDao;
	
	@Override
	public void analyzeAllHost() throws Exception {
		List<HostModel> hostList = rssDao.getHostAllList();
		for(int i = 0; i < hostList.size(); i++) {
			HostModel model = hostList.get(i);
			Map<String, Integer> map = analyzeByHost(model.getHostUrl());
			Iterator<String> iterator = map.keySet().iterator();
			while(iterator.hasNext()) {
				String key = iterator.next();
				Integer count = map.get(key);
				System.out.println(key + ":" + count);
			}
		}
	}
	
	private Map<String,Integer> analyzeByHost(String host) throws Exception {
		List<ArticleModel> articleList = rssDao.getArticleListByHost(host);
		Map<String,Integer> retMap = new HashMap<String, Integer>();
		for(int i = 0; i < articleList.size(); i++) {
			ArticleModel model = articleList.get(i);
			Map<String, Integer> textMap = analyze(model.getText());
			Map<String, Integer> titleMap = analyze(model.getTitle());
			
			Iterator<String> it = textMap.keySet().iterator();
			while(it.hasNext()) {
				String key = it.next();
				Integer count = textMap.get(key);
				Integer retCount = retMap.get(key);
				count = count + TEXT_POINT;
				if(retCount == null || retCount == 0) {
					retMap.put(key, count);
				} else {
					retMap.put(key, retCount + count);
				}
			}
			
			it = titleMap.keySet().iterator();
			while(it.hasNext()) {
				String key = it.next();
				Integer count = textMap.get(key);
				Integer retCount = retMap.get(key);
				count = count + TITLE_POINT;
				if(retCount == null || retCount == 0) {
					retMap.put(key, count);
				} else {
					retMap.put(key, retCount + count);
				}
			}
			
		}
		return retMap;
	}
	
	private Map<String,Integer> analyze(String text) throws Exception {
		Map<String,Integer> textCountMap = new HashMap<String,Integer>();
		// init MorphemeAnalyzer
		MorphemeAnalyzer ma = new MorphemeAnalyzer();

		// create logger, null then System.out is set as a default logger
		// analyze morpheme without any post processing 
		// refine spacing
		// leave the best analyzed result
		// divide result to setences
		ma.createLogger(null);
		List<MExpression> ret = ma.analyze(text);
		ret = ma.postProcess(ret);
		ret = ma.leaveJustBest(ret);
		List<Sentence> sentenceList = ma.divideToSentences(ret);
		
		// result
		for( int j = 0; j < sentenceList.size(); j++ ) {
			Sentence st = sentenceList.get(j);
			System.out.println("" + st.getSentence());
			for( int k = 0; k < st.size(); k++ ) {
				Morpheme first = st.get(k).getFirstMorp();
				if(("NNG".equalsIgnoreCase(first.getTag()) || "NNP".equalsIgnoreCase(first.getTag()) ||
						"NNB".equalsIgnoreCase(first.getTag()))
						&& first.getString().length() > 1) {
					Integer count = textCountMap.get(first.getString());
					if(count == null || count == 0) {
						textCountMap.put(first.getString(), 1);
					} else {
						count++;
						textCountMap.put(first.getString(), count);
					}
				}
			}
		}
		return textCountMap;
	}
}
