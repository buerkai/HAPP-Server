package com.happ.webcore.base.net;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by hyc on 17/2/15.
 */

public class HNetResponse {

	private String requestUrl;

	private String requestMethod;

	private boolean ok;

	private byte[] responseData;

	private Map<String, List<String>> responseHeaders = new HashMap<>();;

	public HNetResponse(String requestUrl, String requestMethod) {
		this.requestMethod = requestMethod;
		this.requestUrl = requestUrl;
	}

	public String getRequestMethod() {
		return requestMethod;
	}

	public void setRequestMethod(String requestMethod) {
		this.requestMethod = requestMethod;
	}

	public String getRequestUrl() {
		return requestUrl;
	}

	public void setRequestUrl(String requestUrl) {
		this.requestUrl = requestUrl;
	}

	public boolean isOk() {
		return ok;
	}

	public void setOk(boolean ok) {
		this.ok = ok;
	}

	public byte[] getResponseData() {
		return responseData;
	}

	public void setResponseData(byte[] responseData) {
		this.responseData = responseData;
	}

	public Map<String, List<String>> getResponseHeaders() {
		return responseHeaders;
	}

	public void setResponseHeaders(Map<String, List<String>> responseHeaders) {
		this.responseHeaders = responseHeaders;
	}

	public void addResponseHeader(String key, String value) {
		if(value==null) {
			return;
		}
		ArrayList<String> tmp = new ArrayList<>();
		tmp.add(value);
		responseHeaders.put(key, tmp);
	}
}
