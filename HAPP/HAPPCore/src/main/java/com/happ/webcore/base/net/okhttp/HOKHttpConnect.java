package com.happ.webcore.base.net.okhttp;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import com.happ.webcore.base.net.HNetConnect;
import com.happ.webcore.base.net.HNetResponse;
import com.happ.webcore.base.net.HSSLSocketFactory;
import com.happ.webcore.base.thread.HExecutor;

import okhttp3.Cache;
import okhttp3.ConnectionPool;
import okhttp3.Dispatcher;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.internal.Util;
import okio.BufferedSink;

public class HOKHttpConnect {

	private OkHttpClient client;
	private OkHttpClient.Builder builder = new OkHttpClient.Builder();
	
	
	public HOKHttpConnect() {
		builder.dispatcher(new Dispatcher(HExecutor.getInstance().getExecutor()));
	}

	/***
	 * 设置https证书相关
	 * 
	 * @param socketFactory
	 * @return
	 */
	public HOKHttpConnect setHttps(HSSLSocketFactory socketFactory) {
		if (socketFactory != null) {
			// HSSLSocketFactory 中已经设置了X509TrustManager
			builder.sslSocketFactory(socketFactory, null);
		}
		return this;
	}

	/***
	 * 设置超时时间
	 * 
	 * @param connectionTimeOut
	 * @param readTimeOut
	 * @return
	 */
	public HOKHttpConnect setTimeOut(int connectionTimeOutMilliSeconds, int readTimeOutMilliSeconds) {
		builder.connectTimeout(connectionTimeOutMilliSeconds, TimeUnit.MILLISECONDS);
		builder.readTimeout(readTimeOutMilliSeconds, TimeUnit.MILLISECONDS);
		return this;
	}

	/***
	 * 设置缓存
	 * 
	 * @param cachePath
	 * @param cacheFileMaxSize
	 * @return
	 */
	public HOKHttpConnect setCach(File cachePath, long cacheFileMaxSize) {
		builder.cache(new Cache(cachePath, cacheFileMaxSize));
		return this;
	}

	/***
	 * 设置连接池
	 * 
	 * @param maxIdleConnections
	 * @param keepAliveDurationSeconds
	 * @return
	 */
	public HOKHttpConnect setConnectionPool(int maxIdleConnections, int keepAliveDurationSeconds) {
		builder.connectionPool(new ConnectionPool(maxIdleConnections, keepAliveDurationSeconds, TimeUnit.SECONDS));
		return this;
	}

	public HOKHttpConnect build() {
		client = builder.build();
		return this;
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
		return request(url, HNetConnect.METHOD_POST, headers, data);
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
		return request(requestUrl, HNetConnect.METHOD_GET, headers, null);
	}
	
	
	
	public HNetResponse request(String url, String method, Map<String, String> headers, byte[] data) {
		Request.Builder builder = new Request.Builder();
		builder.url(url);
		if (headers != null) {
			Set<String> keys = headers.keySet();
			for (String key : keys) {
				builder.addHeader(key, headers.get(key));
			}
		}
		if (data != null) {
			builder.method(method, new RequestBody() {

				@Override
				public void writeTo(BufferedSink paramBufferedSink) throws IOException {
					paramBufferedSink.write(data);
				}

				@Override
				public MediaType contentType() {
					return MediaType.parse(org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE);
				}
			});
		}else {
			builder.method(method, Util.EMPTY_REQUEST);
		}
		try {
			Response response = client.newCall(builder.build()).execute();
			HNetResponse res = new HNetResponse(url, method);
			res.setOk(response.isSuccessful());
			res.setResponseData(response.body().bytes());
			okhttp3.Headers responseHeaders = response.headers();
			Set<String> keys = responseHeaders.names();
			for (String key : keys) {
				res.addResponseHeader(key, responseHeaders.get(key));
			}
			return res;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
