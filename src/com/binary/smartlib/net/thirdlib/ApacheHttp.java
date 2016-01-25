package com.binary.smartlib.net.thirdlib;

import java.io.IOException;
import java.util.List;


import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;


/**
 * 
 * @author yao.guoju
 *
 */
public class ApacheHttp {
	
	/**
	 * http回调
	 * @author yao.guoju
	 *
	 */
	public interface Callback {
		void onSuccess(byte[] response,int code);
		void onError(int code);
		void onStart();
	}

	/**
	 * Http　Get请求
	 * @param url
	 * @param headers
	 * @param params
	 * @param callback4
	 */
	public void get(final String url,final ApacheHttpHeaders headers,final ApacheHttpUrlParams params,final Callback callback) {
		new Thread(new Runnable() {
			public void run() {
				if(callback != null) {
					callback.onStart();
				}
				String getUrl = url;
				int responseCode = HttpStatus.SC_GONE;
				if(params != null) {
					getUrl = params.assembleUrl(url);
				}
				
				HttpClient client = new HttpClient() ;
			    HttpMethod getMethod = new GetMethod(getUrl);
			    
			    if(headers != null) {
				    List<Header> hs = headers.getHeaders();
				    if(hs != null) {
				    	for(Header h : hs) {
				    		getMethod.setRequestHeader(h);
				    	}
				    }
			    }
			    
			    try {
			    	responseCode = client.executeMethod(getMethod);
					if(responseCode == HttpStatus.SC_OK) {
						if(callback != null) {
							callback.onSuccess(getMethod.getResponseBody(), responseCode);
						}
					}
				} catch (HttpException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}finally{
					if(callback == null) {
						callback.onError(responseCode);
					}
				}
			    
			}
		}).start();	
	}
	
	public void post(final String url,final ApacheHttpHeaders headers,final ApacheHttpUrlParams params,final Callback callback) {
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				if(callback != null) {
					callback.onStart();
				}
				String postUrl = url;
				int responseCode = HttpStatus.SC_GONE;
				if(params != null) {
					postUrl = params.assembleUrl(url);
				}
				
				HttpClient client = new HttpClient() ;
			    HttpMethod postMethod = new PostMethod(postUrl);
			    
			    if(headers != null) {
				    List<Header> hs = headers.getHeaders();
				    if(hs != null) {
				    	for(Header h : hs) {
				    		postMethod.setRequestHeader(h);
				    	}
				    }
			    }	
			    
			}
		}).start();
		
	    
	}
	
}
