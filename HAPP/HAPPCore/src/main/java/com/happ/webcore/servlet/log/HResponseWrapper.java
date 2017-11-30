package com.happ.webcore.servlet.log;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import com.happ.webcore.base.conf.HAPPConstance;
import com.happ.webcore.base.log.HLog;

public class HResponseWrapper implements HttpServletResponse {

	private HttpServletResponse response;
	private HLog log;
	private String uuid;
	private String url;

	private HServletOutputStreamWrapper outputStreamWrapper;

	public HResponseWrapper(HttpServletResponse response, HLog log, String uuid, String url) throws IOException {
		this.response = response;
		this.log = log;
		this.uuid = uuid;
		this.url = url;
		outputStreamWrapper = new HServletOutputStreamWrapper(response.getOutputStream());
	}

	@Override
	public ServletOutputStream getOutputStream() throws IOException {
		return outputStreamWrapper;
	}

	public void printLog() {
		StringBuffer sb = new StringBuffer("\n");
		sb.append("==============================send ").append(uuid).append(" ==============================")
				.append("\n");
		if (url != null) {
			sb.append("[url]").append(url).append("\n");
		}
		Map<String, String> headers = new HashMap<String, String>();
		Collection<String> headerNames = response.getHeaderNames();
		for (String headerName : headerNames) {
			headers.put(headerName, response.getHeader(headerName));
		}
		if (headers.size() > 0) {
			sb.append("[header]").append(headers.toString()).append("\n");
		}
		headers.clear();
		headers = null;
		byte[] readData = outputStreamWrapper.getData();
		if (readData != null) {
			sb.append("[body]");
			try {
				sb.append(new String(readData, HAPPConstance.UTF8));
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			sb.append("\n");
		}

		sb.append(
				"==============================================    end    ==============================================");
		log.info(sb.toString());
		sb.reverse();
		sb = null;

	}

	@Override
	public String getCharacterEncoding() {
		return response.getCharacterEncoding();
	}

	@Override
	public String getContentType() {
		return response.getContentType();
	}

	@Override
	public PrintWriter getWriter() throws IOException {
		return response.getWriter();
	}

	@Override
	public void setCharacterEncoding(String paramString) {
		response.setCharacterEncoding(paramString);
	}

	@Override
	public void setContentLength(int paramInt) {
		response.setContentLength(paramInt);
	}

	@Override
	public void setContentLengthLong(long paramLong) {
		response.setContentLengthLong(paramLong);
	}

	@Override
	public void setContentType(String paramString) {
		response.setContentType(paramString);
	}

	@Override
	public void setBufferSize(int paramInt) {
		response.setBufferSize(paramInt);
	}

	@Override
	public int getBufferSize() {
		return response.getBufferSize();
	}

	@Override
	public void flushBuffer() throws IOException {
		response.flushBuffer();
	}

	@Override
	public void resetBuffer() {
		response.resetBuffer();
	}

	@Override
	public boolean isCommitted() {
		return response.isCommitted();
	}

	@Override
	public void reset() {
		response.reset();
	}

	@Override
	public void setLocale(Locale paramLocale) {
		response.setLocale(paramLocale);
	}

	@Override
	public Locale getLocale() {
		return response.getLocale();
	}

	@Override
	public void addCookie(Cookie paramCookie) {
		response.addCookie(paramCookie);
	}

	@Override
	public void addDateHeader(String paramString, long paramLong) {
		response.addDateHeader(paramString, paramLong);
	}

	@Override
	public void addHeader(String paramString1, String paramString2) {
		response.addHeader(paramString1, paramString2);
	}

	@Override
	public void addIntHeader(String paramString, int paramInt) {
		response.addIntHeader(paramString, paramInt);
	}

	@Override
	public boolean containsHeader(String paramString) {
		return response.containsHeader(paramString);
	}

	@Override
	public String encodeRedirectURL(String paramString) {
		return response.encodeRedirectURL(paramString);
	}

	@Override
	public String encodeRedirectUrl(String paramString) {
		return response.encodeRedirectUrl(paramString);
	}

	@Override
	public String encodeURL(String paramString) {
		return response.encodeURL(paramString);
	}

	@Override
	public String encodeUrl(String paramString) {
		return response.encodeUrl(paramString);
	}

	@Override
	public String getHeader(String paramString) {
		return response.getHeader(paramString);
	}

	@Override
	public Collection<String> getHeaderNames() {
		return response.getHeaderNames();
	}

	@Override
	public Collection<String> getHeaders(String paramString) {
		return response.getHeaders(paramString);
	}

	@Override
	public int getStatus() {
		return response.getStatus();
	}

	@Override
	public void sendError(int paramInt) throws IOException {
		response.sendError(paramInt);
	}

	@Override
	public void sendError(int paramInt, String paramString) throws IOException {
		response.sendError(paramInt, paramString);
	}

	@Override
	public void sendRedirect(String paramString) throws IOException {
		response.sendRedirect(paramString);
	}

	@Override
	public void setDateHeader(String paramString, long paramLong) {
		response.setDateHeader(paramString, paramLong);
	}

	@Override
	public void setHeader(String paramString1, String paramString2) {
		response.setHeader(paramString1, paramString2);
	}

	@Override
	public void setIntHeader(String paramString, int paramInt) {
		response.setIntHeader(paramString, paramInt);
	}

	@Override
	public void setStatus(int paramInt) {
		response.setStatus(paramInt);
	}

	@Override
	public void setStatus(int paramInt, String paramString) {
		response.setStatus(paramInt, paramString);
	}

}
