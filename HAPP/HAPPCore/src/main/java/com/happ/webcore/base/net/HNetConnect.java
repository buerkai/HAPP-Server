package com.happ.webcore.base.net;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import java.util.Map;
import java.util.Set;



/**
 * Created by hyc on 17/1/4.
 */

public class HNetConnect {

	public final static String METHOD_OPTIONS = "OPTIONS";
	public final static String METHOD_GET = "GET";
	public final static String METHOD_HEAD = "HEAD";
	public final static String METHOD_POST = "POST";
	public final static String METHOD_PUT = "PUT";
	public final static String METHOD_DELETE = "DELETE";
	public final static String METHOD_TRACE = "TRACE";
	public final static String METHOD_PATCH = "PATCH";

	

	private int _connectTimeOut = 30000;
	private int _readTimeOut = 20000;

	/***
	 * 初始化参数
	 * 
	 * @param connectTimeOut
	 * @param readTimeout
	 */
	public void setTimeOut(int connectTimeOut, int readTimeout) {
		_connectTimeOut = connectTimeOut;
		_readTimeOut = readTimeout;
	}

	/***
	 * POST请求
	 * 
	 * @param url
	 * @param headers
	 * @param data
	 * @return
	 */
	public HNetResponse POST(String url, Map<String, String> headers, byte[] data) {
		return request(url, METHOD_POST, headers, data, _connectTimeOut, _readTimeOut);
	}

	/***
	 * Get请求
	 * 
	 * @param url
	 * @param headers
	 * @return
	 */
	public HNetResponse GET(String url, Map<String, String> headers) {
		return GET(url, headers, null);
	}

	/***
	 * Get请求
	 * 
	 * @param url
	 * @param headers
	 * @param params
	 * @return
	 */
	public HNetResponse GET(String url, Map<String, String> headers, Map<String, String> params) {
		String requestUrl = url;
		if (params != null && params.size() > 0) {
			StringBuffer stringBuffer = new StringBuffer();
			Set<String> keys = params.keySet();
			for (String key : keys) {
				String value = headers.get(key);
				if (value == null) {
					continue;
				}
				stringBuffer.append(key).append("=").append(value).append("&");
			}
			String paramString = stringBuffer.toString();
			paramString = paramString.substring(0, paramString.length() - 1);
			if (url.indexOf("?") > 0) {
				if (url.endsWith("?")) {
					requestUrl += paramString;
				} else {
					requestUrl += "&" + paramString;
				}
			} else {
				requestUrl += "?" + paramString;
			}
		}
		return request(requestUrl, METHOD_GET, headers, null, _connectTimeOut, _readTimeOut);
	}

	/***
	 * 发起请求
	 * 
	 * @param url
	 * @param method
	 * @param headers
	 * @param data
	 * @return
	 */
	public HNetResponse request(String url, String method, Map<String, String> headers, byte[] data, int connectTimeOut,
			int readTimeOut) {
		HNetResponse response = new HNetResponse(url, method);
		InputStream inputStream = null;
		OutputStream outputStream = null;
		HttpURLConnection connection = null;
		try {
			URL u = new URL(url);
			connection = (HttpURLConnection) u.openConnection();
			// 设置参数
			connection.setDoInput(true);
			connection.setDoOutput(true);
			connection.setConnectTimeout(connectTimeOut);
			connection.setReadTimeout(readTimeOut);
			connection.setRequestMethod(method);

			// 设置header
			if (headers != null && headers.size() > 0) {
				Set<String> keys = headers.keySet();
				for (String key : keys) {
					String value = headers.get(key);
					if (value == null) {
						continue;
					}
					connection.setRequestProperty(key, value);
				}
			}
			// 发送数据
			outputStream = connection.getOutputStream();
			if (data != null) {
				outputStream.write(data);
				outputStream.flush();
			}
			outputStream.close();
			inputStream = connection.getInputStream();
			byte[] buffer = new byte[2048];
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			int len = 0;
			while ((len = inputStream.read(buffer)) > 0) {
				bos.write(buffer, 0, len);
			}
			response.setOk(true);
			response.setResponseData(bos.toByteArray());
			response.setResponseHeaders(connection.getHeaderFields());
			return response;
		} catch (Exception e) {
			e.printStackTrace();
			return response;
		} finally {
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			if (connection != null) {
				connection.disconnect();
			}
		}
	}
}
