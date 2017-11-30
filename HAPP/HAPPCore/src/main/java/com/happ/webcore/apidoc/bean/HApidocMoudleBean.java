package com.happ.webcore.apidoc.bean;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class HApidocMoudleBean implements Comparator<HApidocRestApiBean> {

	private String name;
	private int order;
	private List<HApidocRestApiBean> restApi = new ArrayList<HApidocRestApiBean>();

	public HApidocMoudleBean() {
	}

	public HApidocMoudleBean(String description, int order) {
		this.name = description;
		this.order = order;
	}

	public int getOrder() {
		return order;
	}

	public void setOrder(int order) {
		this.order = order;
	}

	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<HApidocRestApiBean> getRestApi() {
		return restApi;
	}

	public void setRestApi(List<HApidocRestApiBean> restApi) {
		this.restApi = restApi;
	}

	/***
	 * 排序
	 */
	public void sort() {
		for (HApidocRestApiBean item : restApi) {
			item.sort();
		}
		restApi.sort(this);
	}

	@Override
	public int compare(HApidocRestApiBean arg0, HApidocRestApiBean arg1) {
		if (arg0.getOrder() > arg1.getOrder()) {
			return 1;
		} else if (arg0.getOrder() == arg1.getOrder()) {
			return 0;
		} else {
			return -1;
		}

	}
}
