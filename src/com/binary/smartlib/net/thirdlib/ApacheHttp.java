package com.binary.smartlib.net.thirdlib;

import java.io.IOException;
import java.io.OutputStream;
import java.io.StringReader;
import java.util.List;


import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.ByteArrayRequestEntity;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.PutMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.StringRequestEntity;



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
	public static void get(final String url,final ApacheHttpHeaders headers,final ApacheHttpUrlParams params,final Callback callback) {
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
				GetMethod getMethod = new GetMethod(getUrl);
			    
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
					}else {
						if(callback != null) {
							callback.onError(responseCode);
						}
					}
				} catch (HttpException e) {
					// TODO Auto-generated catch block
					if(callback != null) {
						callback.onError(responseCode);
					}
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					if(callback != null) {
						callback.onError(responseCode);
					}
					e.printStackTrace();
				}finally{
					
					getMethod.releaseConnection();
				}
			    
			}
		}).start();	
	}
	
	/**
	 * Http Post请求
	 * @param url
	 * @param body
	 * @param headers
	 * @param params
	 * @param callback
	 */
	public static void post(final String url,final byte[] body,final ApacheHttpHeaders headers,final ApacheHttpUrlParams params,final Callback callback) {
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
			    PostMethod postMethod = new PostMethod(postUrl);
			    
			    if(headers != null) {
				    List<Header> hs = headers.getHeaders();
				    if(hs != null) {
				    	for(Header h : hs) {
				    		postMethod.setRequestHeader(h);
				    	}
				    }
			    }
			    if(body != null) {
			    	ByteArrayRequestEntity entity = new ByteArrayRequestEntity(body); 
			    	postMethod.setRequestEntity(entity);
			    }
			    try {
					responseCode = client.executeMethod(postMethod);
					if(responseCode == HttpStatus.SC_OK) {
						if(callback != null) {
							callback.onSuccess(postMethod.getResponseBody(), responseCode);
						}
					}else {
						if(callback != null) {
							callback.onError(responseCode);
						}
					}
				} catch (HttpException e) {
					// TODO Auto-generated catch block
					if(callback != null) {
						callback.onError(responseCode);
					}
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					if(callback != null) {
						callback.onError(responseCode);
					}
					e.printStackTrace();
				}finally {
					
					postMethod.releaseConnection();
				} 
			}
		}).start();
		
	    
	}
	
	
	/**
	 * Http Put请求
	 * @param url
	 * @param body
	 * @param headers
	 * @param params
	 * @param callback
	 */
	public static void put(final String url,final byte[] body,final ApacheHttpHeaders headers,final ApacheHttpUrlParams params,final Callback callback) {
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				if(callback != null) {
					callback.onStart();
				}
				String putUrl = url;
				int responseCode = HttpStatus.SC_GONE;
				if(params != null) {
					putUrl = params.assembleUrl(url);
				}
				
				HttpClient client = new HttpClient() ;
			    PutMethod putMethod = new PutMethod(putUrl);
			    
			    if(headers != null) {
				    List<Header> hs = headers.getHeaders();
				    if(hs != null) {
				    	for(Header h : hs) {
				    		putMethod.setRequestHeader(h);
				    	}
				    }
			    }
			    
			    if(body != null) {
			        ByteArrayRequestEntity entity = new ByteArrayRequestEntity(body); 
			        putMethod.setRequestEntity(entity);
			    }
			    try {
					responseCode = client.executeMethod(putMethod);
					if(responseCode == HttpStatus.SC_OK) {
						if(callback != null) {
							callback.onSuccess(putMethod.getResponseBody(), responseCode);
						}
					}else {
						if(callback != null) {
							callback.onError(responseCode);
						}
					}
					
				} catch (HttpException e) {
					// TODO Auto-generated catch block
					if(callback != null) {
						callback.onError(responseCode);
					}
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					if(callback != null) {
						callback.onError(responseCode);
					}
					e.printStackTrace();
				}finally {		
					putMethod.releaseConnection();
				} 
			}
		}).start(); 
	}
	
}
