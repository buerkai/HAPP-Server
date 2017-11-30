package com.happ.webcore.servlet.log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.security.Principal;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.servlet.AsyncContext;
import javax.servlet.DispatcherType;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpUpgradeHandler;
import javax.servlet.http.Part;

import org.json.JSONArray;
import org.json.JSONObject;

import com.happ.webcore.base.conf.HAPPConstance;
import com.happ.webcore.base.log.HLog;
import com.happ.webcore.servlet.log.HServletInputStreamWrapper.StreamColoseListener;

public class HRequestWrapper implements HttpServletRequest, StreamColoseListener {

	private HttpServletRequest request;
	private HLog log;
	private String uuid;
	private HServletInputStreamWrapper inputStreamWrapper;

	public HRequestWrapper(HttpServletRequest request, HLog log, String uuid) throws IOException {
		this.request = request;
		this.log = log;
		this.uuid = uuid;
		inputStreamWrapper = new HServletInputStreamWrapper(request.getInputStream());
		inputStreamWrapper.setStreamColoseListener(this);
		if (request.getContentLengthLong() <= 0) {
			printLog();
		}
	}

	@Override
	public ServletInputStream getInputStream() throws IOException {
		return inputStreamWrapper;
	}

	@Override
	public void doClose() {
		printLog();
	}

	private boolean printedLog = false;

	public void printLog() {
		if (printedLog) {
			return;
		}
		printedLog = true;
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		StringBuffer sb = new StringBuffer("\n");
		sb.append("==============================receive ").append(uuid).append(" ==============================")
				.append("\n");
		StringBuffer url = httpRequest.getRequestURL();
		if (url != null) {
			sb.append("[url]").append(url).append("\n");
		}
		Map<String, String> headers = new HashMap<String, String>();
		Enumeration<String> headerNames = httpRequest.getHeaderNames();
		while (headerNames.hasMoreElements()) {
			String headerName = headerNames.nextElement();
			headers.put(headerName, httpRequest.getHeader(headerName));
		}
		if (headers.size() > 0) {
			sb.append("[header]").append(headers.toString()).append("\n");
		}
		headers.clear();
		headers = null;
		String method = httpRequest.getMethod();
		if (method != null) {
			sb.append("[method]").append(method).append("\n");
		}
		byte[] readData = inputStreamWrapper.getData();
		if (readData != null) {
			sb.append("[body]");
			try {
				sb.append(new String(readData, HAPPConstance.UTF8));
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			sb.append("\n");
		}
		Map<String, String[]> parameters = request.getParameterMap();
		if (parameters != null && !parameters.isEmpty()) {
			JSONObject data = new JSONObject(parameters);
			sb.append("[parameter]").append(data.toString()).append("\n");
		}
		sb.append(
				"==============================================    end    ==============================================");
		log.info(sb.toString());
		sb.reverse();
		sb = null;
	}

	@Override
	public Object getAttribute(String paramString) {
		return request.getAttribute(paramString);
	}

	@Override
	public Enumeration<String> getAttributeNames() {
		return request.getAttributeNames();
	}

	@Override
	public String getCharacterEncoding() {
		return request.getCharacterEncoding();
	}

	@Override
	public void setCharacterEncoding(String paramString) throws UnsupportedEncodingException {
		request.setCharacterEncoding(paramString);
	}

	@Override
	public int getContentLength() {

		return request.getContentLength();
	}

	@Override
	public long getContentLengthLong() {
		return request.getContentLengthLong();
	}

	@Override
	public String getContentType() {
		return request.getContentType();
	}

	@Override
	public String getParameter(String paramString) {
		return request.getParameter(paramString);
	}

	@Override
	public Enumeration<String> getParameterNames() {
		return request.getParameterNames();
	}

	@Override
	public String[] getParameterValues(String paramString) {
		return request.getParameterValues(paramString);
	}

	@Override
	public Map<String, String[]> getParameterMap() {
		return request.getParameterMap();
	}

	@Override
	public String getProtocol() {
		return request.getProtocol();
	}

	@Override
	public String getScheme() {
		return request.getScheme();
	}

	@Override
	public String getServerName() {
		return request.getServerName();
	}

	@Override
	public int getServerPort() {
		return request.getServerPort();
	}

	@Override
	public BufferedReader getReader() throws IOException {
		return request.getReader();
	}

	@Override
	public String getRemoteAddr() {
		return request.getRemoteAddr();
	}

	@Override
	public String getRemoteHost() {
		return request.getRemoteHost();
	}

	@Override
	public void setAttribute(String paramString, Object paramObject) {
		request.setAttribute(paramString, paramObject);
	}

	@Override
	public void removeAttribute(String paramString) {
		request.removeAttribute(paramString);
	}

	@Override
	public Locale getLocale() {
		return request.getLocale();
	}

	@Override
	public Enumeration<Locale> getLocales() {
		return request.getLocales();
	}

	@Override
	public boolean isSecure() {
		return request.isSecure();
	}

	@Override
	public RequestDispatcher getRequestDispatcher(String paramString) {
		return request.getRequestDispatcher(paramString);
	}

	@Override
	public String getRealPath(String paramString) {
		return request.getRealPath(paramString);
	}

	@Override
	public int getRemotePort() {
		return request.getRemotePort();
	}

	@Override
	public String getLocalName() {
		return request.getLocalName();
	}

	@Override
	public String getLocalAddr() {
		return request.getLocalAddr();
	}

	@Override
	public int getLocalPort() {
		return request.getLocalPort();
	}

	@Override
	public ServletContext getServletContext() {
		return request.getServletContext();
	}

	@Override
	public AsyncContext startAsync() throws IllegalStateException {
		return request.startAsync();
	}

	@Override
	public AsyncContext startAsync(ServletRequest paramServletRequest, ServletResponse paramServletResponse)
			throws IllegalStateException {
		return request.startAsync(paramServletRequest, paramServletResponse);
	}

	@Override
	public boolean isAsyncStarted() {
		return request.isAsyncStarted();
	}

	@Override
	public boolean isAsyncSupported() {
		return request.isAsyncSupported();
	}

	@Override
	public AsyncContext getAsyncContext() {
		return request.getAsyncContext();
	}

	@Override
	public DispatcherType getDispatcherType() {
		return request.getDispatcherType();
	}

	@Override
	public boolean authenticate(HttpServletResponse arg0) throws IOException, ServletException {
		return request.authenticate(arg0);
	}

	@Override
	public String changeSessionId() {
		return request.changeSessionId();
	}

	@Override
	public String getAuthType() {
		return request.getAuthType();
	}

	@Override
	public String getContextPath() {
		return request.getContextPath();
	}

	@Override
	public Cookie[] getCookies() {
		return request.getCookies();
	}

	@Override
	public long getDateHeader(String arg0) {
		return request.getDateHeader(arg0);
	}

	@Override
	public String getHeader(String arg0) {
		return request.getHeader(arg0);
	}

	@Override
	public Enumeration<String> getHeaderNames() {
		return request.getHeaderNames();
	}

	@Override
	public Enumeration<String> getHeaders(String arg0) {
		return request.getHeaders(arg0);
	}

	@Override
	public int getIntHeader(String arg0) {
		return request.getIntHeader(arg0);
	}

	@Override
	public String getMethod() {
		return request.getMethod();
	}

	@Override
	public Part getPart(String arg0) throws IOException, ServletException {
		return request.getPart(arg0);
	}

	@Override
	public Collection<Part> getParts() throws IOException, ServletException {
		return request.getParts();
	}

	@Override
	public String getPathInfo() {
		return request.getPathInfo();
	}

	@Override
	public String getPathTranslated() {
		return request.getPathTranslated();
	}

	@Override
	public String getQueryString() {
		return request.getQueryString();
	}

	@Override
	public String getRemoteUser() {
		return request.getRemoteUser();
	}

	@Override
	public String getRequestURI() {
		return request.getRequestURI();
	}

	@Override
	public StringBuffer getRequestURL() {
		return request.getRequestURL();
	}

	@Override
	public String getRequestedSessionId() {
		return request.getRequestedSessionId();
	}

	@Override
	public String getServletPath() {
		return request.getServletPath();
	}

	@Override
	public HttpSession getSession() {
		return request.getSession();
	}

	@Override
	public HttpSession getSession(boolean arg0) {
		return request.getSession(arg0);
	}

	@Override
	public Principal getUserPrincipal() {
		return request.getUserPrincipal();
	}

	@Override
	public boolean isRequestedSessionIdFromCookie() {
		return request.isRequestedSessionIdFromCookie();
	}

	@Override
	public boolean isRequestedSessionIdFromURL() {
		return request.isRequestedSessionIdFromURL();
	}

	@Override
	public boolean isRequestedSessionIdFromUrl() {
		return request.isRequestedSessionIdFromUrl();
	}

	@Override
	public boolean isRequestedSessionIdValid() {
		return request.isRequestedSessionIdValid();
	}

	@Override
	public boolean isUserInRole(String arg0) {
		return request.isUserInRole(arg0);
	}

	@Override
	public void login(String arg0, String arg1) throws ServletException {
		request.login(arg0, arg1);

	}

	@Override
	public void logout() throws ServletException {
		request.logout();
	}

	@Override
	public <T extends HttpUpgradeHandler> T upgrade(Class<T> arg0) throws IOException, ServletException {
		return request.upgrade(arg0);
	}

}
