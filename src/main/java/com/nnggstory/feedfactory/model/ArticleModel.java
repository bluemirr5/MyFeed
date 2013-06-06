package com.nnggstory.feedfactory.model;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * 실제 하나의 feed를 담기 위한 모델 클래스.
 * 
 * @author bluemirr5
 *
 */
public class ArticleModel {
	private String link = null;
	private String host = null;
	private String title = null;
	private String description = null;
	private String text = null;
	private String pubDate = null;
	private List<String> categorys = null;
	private long pubDateToLong = 0;
	
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getPubDate() {
		return pubDate;
	}
	public void setPubDate(String pubDate) throws ParseException {
		DateFormat dateFormatterRssPubDate = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss Z", Locale.ENGLISH);
		Date pubDateForLong = dateFormatterRssPubDate.parse(pubDate);
		this.pubDateToLong = pubDateForLong.getTime();
		this.pubDate = pubDate;
	}
	public List<String> getCategorys() {
		return categorys;
	}
	public void setCategorys(List<String> categorys) {
		this.categorys = categorys;
	}
	public long getPubDateToLong() {
		return pubDateToLong;
	}
	public void setPubDateToLong(long pubDateToLong) {
		this.pubDateToLong = pubDateToLong;
	}
}