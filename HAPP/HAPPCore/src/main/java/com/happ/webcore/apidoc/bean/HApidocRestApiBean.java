package com.happ.webcore.apidoc.bean;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import com.happ.webcore.apidoc.HapidocRestfulAPIType;

public class HApidocRestApiBean implements Comparator<HApidocParamBean> {

	private String url;
	private String description;
	private String name;
	private HapidocRestfulAPIType type;
	private int order;

	private List<HApidocParamBean> inParams = new ArrayList<HApidocParamBean>();
	private List<HApidocParamBean> outParams = new ArrayList<HApidocParamBean>();

	public HApidocRestApiBean() {

	}

	public HApidocRestApiBean(String url, String description, HapidocRestfulAPIType type, int order) {
		this.url = url;
		this.description = description;
		this.type = type;
		this.order = order;
	}

	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getOrder() {
		return order;
	}

	public void setOrder(int order) {
		this.order = order;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public HapidocRestfulAPIType getType() {
		return type;
	}

	public void setType(HapidocRestfulAPIType type) {
		this.type = type;
	}

	public List<HApidocParamBean> getInParams() {
		return inParams;
	}

	public void setInParams(List<HApidocParamBean> inParams) {
		this.inParams = inParams;
	}

	public List<HApidocParamBean> getOutParams() {
		return outParams;
	}

	public void setOutParams(List<HApidocParamBean> outParams) {
		this.outParams = outParams;
	}

	/***
	 * 排序
	 */
	public void sort() {
		outParams.sort(this);
		inParams.sort(this);
	}

	@Override
	public int compare(HApidocParamBean arg0, HApidocParamBean arg1) {
		if (arg0.getOrder() > arg1.getOrder()) {
			return 1;
		} else if (arg0.getOrder() == arg1.getOrder()) {
			return 0;
		} else {
			return -1;
		}
	}

}
