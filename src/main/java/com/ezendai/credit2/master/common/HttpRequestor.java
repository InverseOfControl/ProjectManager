package com.ezendai.credit2.master.common;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.Map;

/**
 * 发送 HTTP GET POST 
 * @author Ivan
 *
 */
public class HttpRequestor {
	
	/** 请求字符集 **/
	private String charset = "utf-8";
	/** 设置连接主机超时（单位：毫秒） **/
	private Integer connectTimeout = 990000;
	/** 设置从主机读取数据超时（单位：毫秒） **/
	private Integer socketTimeout = 990000;
	/** 代理服务器IP **/
	private String proxyHost = null;
	/** 代理服务器端口 **/
	private Integer proxyPort = null;
	
	/**
	 * Do GET request
	 * 
	 * @param url
	 * @return
	 * @throws Exception
	 * @throws IOException
	 */
	public String doGet(String url) throws Exception {
		URL localURL = new URL(url);
		URLConnection connection = openConnection(localURL);
		HttpURLConnection httpURLConnection = (HttpURLConnection) connection;
		
		/** 设置超时时间 **/
		renderRequest(httpURLConnection);
		
		httpURLConnection.setRequestProperty("Accept-Charset", charset);
		httpURLConnection.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
		
		InputStream inputStream = null;
		InputStreamReader inputStreamReader = null;
		BufferedReader reader = null;
		StringBuffer resultBuffer = new StringBuffer();
		String tempLine = null;
		int responseCode = httpURLConnection.getResponseCode();
		if (responseCode != HttpURLConnection.HTTP_OK) {
			throw new Exception("HTTP Request is not success, Response code is " + httpURLConnection.getResponseCode());
		}
		
		try {
			inputStream = httpURLConnection.getInputStream();
			inputStreamReader = new InputStreamReader(inputStream);
			reader = new BufferedReader(inputStreamReader);
			while ((tempLine = reader.readLine()) != null) {
				resultBuffer.append(tempLine);
			}
		} finally {
			if (reader != null) {
				reader.close();
			}
			if (inputStreamReader != null) {
				inputStreamReader.close();
			}
			if (inputStream != null) {
				inputStream.close();
			}
		}
		return resultBuffer.toString();
	}
	
	/**
	 * Do POST request
	 * 
	 * @param url
	 * @param parameterMap
	 * @return
	 * @throws Exception
	 */
	public String doPost(String url, Map parameterMap) throws Exception {
		/* Translate parameter map to parameter date string */
		StringBuffer parameterBuffer = new StringBuffer();
		if (parameterMap != null) {
			Iterator iterator = parameterMap.keySet().iterator();
			String key = null;
			String value = null;
			while (iterator.hasNext()) {
				key = (String) iterator.next();
				if (parameterMap.get(key) != null) {
					value = (String) parameterMap.get(key);
				} else {
					value = "";
				}
				/** 将参数进行转码 **/
				value = URLEncoder.encode(value, charset);
				parameterBuffer.append(key).append("=").append(value);
				if (iterator.hasNext()) {
					parameterBuffer.append("&");
				}
			}
		}
		
		System.out.println(url + "POST parameter : " + parameterBuffer.toString());
		
		URL localURL = new URL(url);
		URLConnection connection = openConnection(localURL);
		HttpURLConnection httpURLConnection = (HttpURLConnection) connection;
		
		/** 设置超时时间 **/
		renderRequest(httpURLConnection);
		
		httpURLConnection.setDoOutput(true);
		httpURLConnection.setRequestMethod("POST");
		httpURLConnection.setRequestProperty("Accept-Charset", charset);
		httpURLConnection.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
		httpURLConnection.setRequestProperty("Content-Length",String.valueOf(parameterBuffer.length()));
		httpURLConnection.setRequestProperty("contentType", charset);
		
		OutputStream outputStream = null;
		OutputStreamWriter outputStreamWriter = null;
		InputStream inputStream = null;
		InputStreamReader inputStreamReader = null;
		BufferedReader reader = null;
		StringBuffer resultBuffer = new StringBuffer();
		String tempLine = null;
		
		try {
			outputStream = httpURLConnection.getOutputStream();
			outputStreamWriter = new OutputStreamWriter(outputStream);
			
			outputStreamWriter.write(parameterBuffer.toString());
			outputStreamWriter.flush();
			int responseCode = httpURLConnection.getResponseCode();
			if (responseCode != HttpURLConnection.HTTP_OK) {
				throw new Exception("HTTP Request is not success, Response code is " + httpURLConnection.getResponseCode());
			}
			inputStream = httpURLConnection.getInputStream();
			inputStreamReader = new InputStreamReader(inputStream);
			reader = new BufferedReader(inputStreamReader);
			while ((tempLine = reader.readLine()) != null) {
				resultBuffer.append(tempLine);
			}
		} finally {
			if (outputStreamWriter != null) {
				outputStreamWriter.close();
			}
			if (outputStream != null) {
				outputStream.close();
			}
			if (reader != null) {
				reader.close();
			}
			if (inputStreamReader != null) {
				inputStreamReader.close();
			}
			if (inputStream != null) {
				inputStream.close();
			}
		}
		return resultBuffer.toString();
	}
	
	private URLConnection openConnection(URL localURL) throws IOException {
		URLConnection connection;
		if (proxyHost != null && proxyPort != null) {
			Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(
					proxyHost, proxyPort));
			connection = localURL.openConnection(proxy);
		} else {
			connection = localURL.openConnection();
		}
		return connection;
	}
	
	/**
	 * Render request according setting
	 * 
	 * @param request
	 */
	private void renderRequest(URLConnection connection) {
		if (connectTimeout != null) {
			connection.setConnectTimeout(connectTimeout);
		}
		if (socketTimeout != null) {
			connection.setReadTimeout(socketTimeout);
		}
	}
	
	/*
	 * Getter & Setter
	 */
	public Integer getConnectTimeout() {
		return connectTimeout;
	}
	
	public void setConnectTimeout(Integer connectTimeout) {
		this.connectTimeout = connectTimeout;
	}
	
	public Integer getSocketTimeout() {
		return socketTimeout;
	}
	
	public void setSocketTimeout(Integer socketTimeout) {
		this.socketTimeout = socketTimeout;
	}
	
	public String getProxyHost() {
		return proxyHost;
	}
	
	public void setProxyHost(String proxyHost) {
		this.proxyHost = proxyHost;
	}
	
	public Integer getProxyPort() {
		return proxyPort;
	}
	
	public void setProxyPort(Integer proxyPort) {
		this.proxyPort = proxyPort;
	}
	
	public String getCharset() {
		return charset;
	}
	
	public void setCharset(String charset) {
		this.charset = charset;
	}
	
}