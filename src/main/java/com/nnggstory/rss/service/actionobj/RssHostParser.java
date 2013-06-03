package com.nnggstory.rss.service.actionobj;

import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.nnggstory.rss.model.HostModel;

public class RssHostParser extends DefaultHandler {
	private StringBuilder stringBuilder = null;
	private HostModel hostModel = null;
	private boolean hostFieldFlag = true;

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
		if (qName.equalsIgnoreCase("channel")) {
			hostModel = new HostModel();
		}
		if (qName.equalsIgnoreCase("item") || qName.equalsIgnoreCase("image")) {
			hostFieldFlag = false;
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
		
		if (qName.equalsIgnoreCase("title") && hostFieldFlag) {
			hostModel.setTitle(stringBuilder.toString().trim());
        }
        else if (qName.equalsIgnoreCase("url")) {
        	hostModel.setImgUrl(stringBuilder.toString().trim());
        }
        else if (qName.equalsIgnoreCase("description") && hostFieldFlag) {
        	hostModel.setDescription(stringBuilder.toString().trim());
		}
		if (qName.equalsIgnoreCase("item") || qName.equalsIgnoreCase("image")) {
			hostFieldFlag = true;
		}
		stringBuilder.setLength(0);
	}
	
	public void parser(InputStream rssIs) throws ParserConfigurationException, SAXException, IOException {
		SAXParser parser = SAXParserFactory.newInstance().newSAXParser();
		parser.parse(rssIs, this); 
	}

	public HostModel getHostModel(String hostUrl) {
		hostModel.setHostUrl(hostUrl);
		return hostModel;
	}
}