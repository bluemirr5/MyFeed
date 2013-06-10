package com.nnggstory.feedfactory.model;

import java.util.List;

public class RssPulishChannelModel {
	private String title = null;
	private String link = null;
	private String description = null;
	private String language = null;
	private String pubDate = null;
	private String generator = null;
	private String managingEditor = null;
	private RssPublishImageModel image = null;
	private List<RssPublishItemModel> item = null;
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
	public String getPubDate() {
		return pubDate;
	}
	public void setPubDate(String pubDate) {
		this.pubDate = pubDate;
	}
	public String getGenerator() {
		return generator;
	}
	public void setGenerator(String generator) {
		this.generator = generator;
	}
	public String getManagingEditor() {
		return managingEditor;
	}
	public void setManagingEditor(String managingEditor) {
		this.managingEditor = managingEditor;
	}
	public RssPublishImageModel getImage() {
		return image;
	}
	public void setImage(RssPublishImageModel image) {
		this.image = image;
	}
	public List<RssPublishItemModel> getItem() {
		return item;
	}
	public void setItem(List<RssPublishItemModel> item) {
		this.item = item;
	}
}

class RssPublishImageModel {
	private String title = null;
	private String url = null;
	private String link = null;
	private String description = null;
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
}