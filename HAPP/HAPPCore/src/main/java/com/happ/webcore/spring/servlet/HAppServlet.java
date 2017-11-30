package com.happ.webcore.spring.servlet;

import java.io.IOException;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;
import org.springframework.web.util.WebUtils;

import com.happ.webcore.base.conf.HAPPConstance;
import com.happ.webcore.base.log.HLog;

@SuppressWarnings("serial")
public class HAppServlet extends DispatcherServlet {

	private HLog log = new HLog("app");

	public HAppServlet() {
		super();
	}

	public HAppServlet(WebApplicationContext webApplicationContext) {
		super(webApplicationContext);
	}

	@Override
	protected void doService(HttpServletRequest arg0, HttpServletResponse arg1) throws Exception {
		try {
			super.doService(arg0, arg1);
		} catch (Throwable e) {
			log.error("执行未知异常", e);
			arg1.setHeader("Content-Type", "application/json;charset=UTF-8");
			arg1.getOutputStream().write(HAPPConstance.ERROR_UNKNOW.toString().getBytes(HAPPConstance.UTF8));
			arg1.getOutputStream().flush();
		}
	}

	@Override
	protected void doDispatch(HttpServletRequest arg0, HttpServletResponse arg1) throws Exception {
		super.doDispatch(arg0, arg1);
	}

	
	
	@Override
	public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {
		super.service(req, res);
	}

	@Override
	protected void noHandlerFound(HttpServletRequest request, HttpServletResponse response) throws Exception {
		super.noHandlerFound(request, response);
	}

	@Override
	protected void onRefresh(ApplicationContext context) {
		// TODO Auto-generated method stub
		super.onRefresh(context);
	}

	
	@Override
	protected ModelAndView processHandlerException(HttpServletRequest request, HttpServletResponse response,
			Object handler, Exception ex) throws Exception {
		log.error("执行未知异常", ex);
		response.setHeader("Content-Type", "application/json;charset=UTF-8");
		response.getOutputStream().write(HAPPConstance.ERROR_SYSTEM.toString().getBytes(HAPPConstance.UTF8));
		response.getOutputStream().flush();
		return null;
	}

	@Override
	protected void render(ModelAndView mv, HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		super.render(mv, request, response);
	}

	@Override
	protected View resolveViewName(String viewName, Map<String, Object> model, Locale locale,
			HttpServletRequest request) throws Exception {
		// TODO Auto-generated method stub
		return super.resolveViewName(viewName, model, locale, request);
	}

}
