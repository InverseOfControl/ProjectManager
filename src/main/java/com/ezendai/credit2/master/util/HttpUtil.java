package com.ezendai.credit2.master.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;


public class HttpUtil {


	/**
	 * GET方式提交请求
	 * 
	 * @param url
	 *            请求地址
	 * @return 响应结果
	 * @throws MalformedURLException
	 * @throws IOException
	 */
	public static String sendRequest(String url) throws MalformedURLException, IOException {
		StringBuffer sb = new StringBuffer();
		HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
		conn.setRequestMethod("GET");
		conn.setConnectTimeout(30000);
		conn.setUseCaches(false);
		BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		String str = null;
		while ((str = reader.readLine()) != null) {
			sb.append(str);
		}
		return sb.toString();
	}

	/**
	 * POST方式提交请求
	 * 
	 * @param url
	 *            请求地址
	 * @param body
	 *            请求报文
	 * @param json
	 *            是否json请求
	 * @return
	 * @throws IOException
	 */
	public static String sendPostRequest(String url, String param, boolean json) throws IOException {
		StringBuffer sb = new StringBuffer();
		HttpURLConnection conn;
		BufferedReader reader = null;
		OutputStream os = null;
		try {
			conn = (HttpURLConnection) new URL(url).openConnection();
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setUseCaches(false);
			conn.setRequestMethod("POST");
			if (json) {
				conn.setRequestProperty("Content-type", "application/json");
			}
			conn.setConnectTimeout(30000);
			if (StringUtils.isNotEmpty(param)) {
				os = conn.getOutputStream();
				os.write(param.getBytes("UTF-8"));
			}
			reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
			String str = null;
			while ((str = reader.readLine()) != null) {
				sb.append(str);
			}
			return sb.toString();
		} catch (IOException e) {
			e.printStackTrace();
			throw e;
		} finally {
			IOUtils.closeQuietly(reader);
			IOUtils.closeQuietly(os);
			IOUtils.closeQuietly(os);
		}
	}

	/**
	 * 
	 * @param utfString
	 * @return
	 */
	public static String convert(String utfString) {
		StringBuilder sb = new StringBuilder();
		int i = -1;
		int pos = 0;

		while ((i = utfString.indexOf("\\u", pos)) != -1) {
			sb.append(utfString.substring(pos, i));
			if (i + 5 < utfString.length()) {
				pos = i + 6;
				sb.append((char) Integer.parseInt(utfString.substring(i + 2, i + 6), 16));
			}
		}

		return sb.toString();
	}

}