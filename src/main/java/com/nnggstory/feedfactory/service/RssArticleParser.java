package com.nnggstory.feedfactory.service;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.nnggstory.feedfactory.model.ArticleModel;


public class RssArticleParser extends DefaultHandler {
	private StringBuilder stringBuilder = null;
	private boolean itemFieldFlag = false;
	private List<ArticleModel> articleList = new ArrayList<ArticleModel>();
	private ArticleModel curArticle = null;

	@Override
	public void startDocument() throws SAXException {
		super.startDocument();
		this.stringBuilder = new StringBuilder();
	}

	@Override
	public void endDocument() throws SAXException {
		super.endDocument();
	}

	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		super.startElement(uri, localName, qName, attributes);
		if (qName.equalsIgnoreCase("item")) {
			itemFieldFlag = true;
			curArticle = new ArticleModel();
			curArticle.setCategorys(new ArrayList<String>());
		}
	}
	
	@Override
	public void characters(char[] ch, int start, int length)
			throws SAXException {
		super.characters(ch, start, length);
		stringBuilder.append(ch, start, length);
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		super.endElement(uri, localName, qName);
		
		if (qName.equalsIgnoreCase("title") && itemFieldFlag) {
			curArticle.setTitle(stringBuilder.toString().trim());
        }
        else if (qName.equalsIgnoreCase("description") && itemFieldFlag) {
        	curArticle.setDescription(stringBuilder.toString().trim());
        }
        else if (qName.equalsIgnoreCase("link") && itemFieldFlag) {
        	curArticle.setLink(stringBuilder.toString().trim());
		}
        else if (qName.equalsIgnoreCase("pubDate") && itemFieldFlag) {
        	try {
				curArticle.setPubDate(stringBuilder.toString().trim());
			} catch (ParseException e) {
				e.printStackTrace();
			}
        } else if(qName.equalsIgnoreCase("category") && itemFieldFlag) {
        	curArticle.getCategorys().add(stringBuilder.toString().trim());
        }
		if (qName.equalsIgnoreCase("item")) {
			itemFieldFlag = false;
			articleList.add(curArticle);
		}
		stringBuilder.setLength(0);
	}
	
	public void parser(InputStream rssIs) throws ParserConfigurationException, SAXException, IOException {
		SAXParser parser = SAXParserFactory.newInstance().newSAXParser();
		parser.parse(rssIs, this); 
	}
	
	public List<ArticleModel> getArticleList(String host) {
		for (int i = 0; i < articleList.size(); i++) {
			articleList.get(i).setHost(host);
		}
		return articleList;
	}
}