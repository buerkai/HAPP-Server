package com.happ.webcore.apidoc;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import com.happ.webcore.apidoc.annotation.HApidocMoudle;
import com.happ.webcore.apidoc.annotation.HApidocParam;
import com.happ.webcore.apidoc.annotation.HApidocParamIn;
import com.happ.webcore.apidoc.annotation.HApidocParamOut;
import com.happ.webcore.apidoc.annotation.HApidocRestfulAPI;
import com.happ.webcore.apidoc.bean.HApidocMoudleBean;
import com.happ.webcore.apidoc.bean.HApidocParamBean;
import com.happ.webcore.apidoc.bean.HApidocRestApiBean;

public abstract class HApidocImpl implements HApidoc {
	protected List<HApidocMoudleBean> moudles = new ArrayList<HApidocMoudleBean>();

	// 作者
	protected String author;
	// 公司
	protected String commpany;
	//
	protected String mail;

	@Override
	public void setAuthorInfo(String author, String commpany, String mail) {
		this.author = author;
		this.commpany = commpany;
		this.mail = mail;

	}

	/***
	 * 添加一个类
	 * 
	 * @param testClass
	 */
	@Override
	public boolean addClass(Class<?> testClass) {
		if (!testClass.isAnnotationPresent(HApidocMoudle.class)) {
			return false;
		}
		parseClass(testClass);
		return true;
	}

	private void parseClass(Class<?> testClass) {
		HApidocMoudle moudle = testClass.getDeclaredAnnotation(HApidocMoudle.class);
		if (moudle == null) {
			return;
		}
		HApidocMoudleBean moudleBean = new HApidocMoudleBean();
		moudleBean.setName(moudle.name());
		moudleBean.setOrder(moudle.order());

		Method methods[] = testClass.getDeclaredMethods();
		for (Method item : methods) {
			parseCLassMethod(item, moudleBean);
		}
		moudleBean.sort();
		moudles.add(moudleBean);
	}

	private void parseCLassMethod(Method method, HApidocMoudleBean moudle) {
		if (!method.isAnnotationPresent(HApidocRestfulAPI.class)) {
			return;
		}
		HApidocRestfulAPI restfulapi = method.getDeclaredAnnotation(HApidocRestfulAPI.class);
		HApidocRestApiBean bean = new HApidocRestApiBean();
		bean.setDescription(restfulapi.description());
		bean.setOrder(restfulapi.order());
		bean.setType(restfulapi.type());
		bean.setUrl(restfulapi.url());
		bean.setName(restfulapi.name());

		parseClassMethodParamIn(method, bean);
		parseClassMethodParamOut(method, bean);
		moudle.getRestApi().add(bean);
	}

	private void parseClassMethodParamIn(Method method, HApidocRestApiBean apibean) {
		HApidocParamIn in = method.getDeclaredAnnotation(HApidocParamIn.class);
		if (in == null) {
			return;
		}
		HApidocParam ps[] = in.value();
		for (HApidocParam item : ps) {
			HApidocParamBean bean = new HApidocParamBean();
			bean.setDescription(item.description());
			bean.setIn(true);
			bean.setLength(item.length());
			bean.setName(item.name());
			bean.setOrder(item.order());
			bean.setRequire(item.require());
			bean.setType(item.type());
			apibean.getInParams().add(bean);
		}
	}

	private void parseClassMethodParamOut(Method method, HApidocRestApiBean apibean) {
		HApidocParamOut out = method.getDeclaredAnnotation(HApidocParamOut.class);
		if (out == null) {
			return;
		}
		HApidocParam ps[] = out.value();
		for (HApidocParam item : ps) {
			HApidocParamBean bean = new HApidocParamBean();
			bean.setDescription(item.description());
			bean.setIn(false);
			bean.setLength(item.length());
			bean.setName(item.name());
			bean.setOrder(item.order());
			bean.setRequire(item.require());
			bean.setType(item.type());
			apibean.getOutParams().add(bean);
		}
	}

}
