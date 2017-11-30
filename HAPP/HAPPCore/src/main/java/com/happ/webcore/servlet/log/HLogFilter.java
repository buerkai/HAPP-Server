package com.happ.webcore.servlet.log;

import java.io.IOException;

import java.util.UUID;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.happ.webcore.base.log.HLog;

public class HLogFilter implements Filter {

	private HLog log;

	@Override
	public void destroy() {
		log = null;
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		log = new HLog("APP");
	}

	@Override
	public void doFilter(ServletRequest arg0, ServletResponse arg1, FilterChain chain) throws IOException, ServletException {
		if (!(arg0 instanceof HttpServletRequest)) {
			chain.doFilter(arg0, arg1);
			return;
		}
		String uuid = UUID.randomUUID().toString();
		HRequestWrapper requestWrapper = new HRequestWrapper((HttpServletRequest) arg0, log, uuid);
		HResponseWrapper responseWrapper = new HResponseWrapper((HttpServletResponse) arg1, log, uuid, requestWrapper.getRequestURL().toString());
		chain.doFilter(requestWrapper, responseWrapper);
		requestWrapper.printLog();
		responseWrapper.printLog();
	}

}
