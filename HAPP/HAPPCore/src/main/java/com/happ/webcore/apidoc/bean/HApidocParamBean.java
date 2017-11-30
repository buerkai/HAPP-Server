package com.happ.webcore.apidoc.bean;

import com.happ.webcore.apidoc.HApidocParamType;

/***
 * 参数类
 * @author Administrator
 *
 */
public class HApidocParamBean {

	private String name;
	private String description;
	private boolean require = false;
	private HApidocParamType type;
	private int length;
	private boolean isIn;
	private int order;

	public HApidocParamBean() {
	}

	public HApidocParamBean(String name, String description, boolean require, HApidocParamType type, int length,
			boolean isIn,int order) {
		this.name = name;
		this.description = description;
		this.require = require;
		this.type = type;
		this.length = length;
		this.isIn = isIn;
		this.order=order;
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public boolean isRequire() {
		return require;
	}

	public void setRequire(boolean require) {
		this.require = require;
	}

	public HApidocParamType getType() {
		return type;
	}

	public void setType(HApidocParamType type) {
		this.type = type;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public boolean isIn() {
		return isIn;
	}

	public void setIn(boolean isIn) {
		this.isIn = isIn;
	}

}
