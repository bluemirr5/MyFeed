package com.nnggstory.rss.service.actionobj;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

public class RssRequestManager {
	public InputStream getRequest(String requestUrl) throws Exception {
		HttpClient httpclient = new DefaultHttpClient();
		
		try{
			HttpGet get = new HttpGet(requestUrl);
			
			HttpResponse response = httpclient.execute(get);
			String contentsBody = EntityUtils.toString(response.getEntity(), "UTF-8");
			
			if(response.getStatusLine().getStatusCode() == 200){
				InputStream bai = new ByteArrayInputStream(contentsBody.getBytes("UTF-8"));
				return bai;
			}else{
				throw new Exception();
			}
		}finally{
			httpclient.getConnectionManager().shutdown();
		}
	}
}
