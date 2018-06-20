package com.xin.springboot.web.servlet.processor;

import com.suntek.eap.web.AppException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.impl.client.CloseableHttpClient;

import java.io.IOException;

/**
 * HttpClient Helper
 * @author zt
 * @since 
 * @version 2015-1-16
 * @Copyright (C)2015 , Suntektech
 */
public class HttpClientHelper
{
	
	private static int TIMEOUT_READ = 2000;
	
	private static int TIMEOUT_CONN = 2000;
	
	private static RequestConfig conf;
	
	
	static {		
		conf = RequestConfig.custom().setSocketTimeout(TIMEOUT_READ).setConnectTimeout(TIMEOUT_CONN).build();				
	}
	
	
	public static CloseableHttpResponse invoke(CloseableHttpClient client, HttpRequestBase method) throws AppException
	{
		return invoke(client, method, true);
	}
	
	public static CloseableHttpResponse invoke(CloseableHttpClient client, HttpRequestBase method, boolean closeResponse) throws AppException
	{
		method.setConfig(conf);
		
		CloseableHttpResponse response = null;
		
		try {
			response = client.execute(method);			
		} catch (Exception e) {
			throw new AppException(e);
		} finally {
			if (closeResponse) { close(response); }
		}
		
		int statusCode = response.getStatusLine().getStatusCode();

		if (statusCode == 200 || statusCode == 302) {
			return response;
		}
		
		int error = response.getStatusLine().getStatusCode();
		String message = response.getStatusLine().toString();
		
		throw new AppException(error, message);
	} 
	
	private static void close(CloseableHttpResponse response)
	{
		if (response != null) {
			try { response.close(); } catch (IOException e) { }
		}
	}
}
