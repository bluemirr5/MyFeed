package com.nnggstory.feedfactory.model;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * 실제 하나의 feed를 담기 위한 모델 클래스.
 * 
 * @author bluemirr5
 *
 */
public class ArticleModel extends RssPublishItemModel {
	private long pubDateToLong = 0;

	@Override
	public void setPubDate(String pubDate) {
		DateFormat dateFormatterRssPubDate = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss Z", Locale.ENGLISH);
		Date pubDateForLong = null;
		try {
			pubDateForLong = dateFormatterRssPubDate.parse(pubDate);
			this.pubDateToLong = pubDateForLong.getTime();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		super.setPubDate(pubDate);
	}
	public long getPubDateToLong() {
		return pubDateToLong;
	}
	public void setPubDateToLong(long pubDateToLong) {
		this.pubDateToLong = pubDateToLong;
	}
}