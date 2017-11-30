package com.happ.webcore.base;

import java.util.List;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import com.happ.webcore.base.exception.HException;

@JacksonXmlRootElement(namespace="HAPPResponse",localName="Response")
public class HResponse {

	
	private Integer code = 0;


	private List<Object> data;


	private String msg;


	private Object pageIndex;


	private Object pageSize;
	
	
	private Object pageTotal;

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public List<Object> getData() {
		return data;
	}

	public void setData(List<Object> data) {
		this.data = data;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Object getPageIndex() {
		return pageIndex;
	}

	public void setPageIndex(Object pageIndex) {
		this.pageIndex = pageIndex;
	}

	public Object getPageSize() {
		return pageSize;
	}

	public void setPageSize(Object pageSize) {
		this.pageSize = pageSize;
	}
	

	public Object getPageTotal() {
		return pageTotal;
	}

	public void setPageTotal(Object pageTotal) {
		this.pageTotal = pageTotal;
	}

	public HResponse(Integer code, List<Object> data, String msg, Object pageIndex, Object pageSize,Object pageTotal) {
		this.code = code;
		this.data = data;
		this.msg = msg;
		this.pageIndex = pageIndex;
		this.pageSize = pageSize;
		this.pageTotal=pageTotal;
	}

	public HResponse(Integer code, List<Object> data, String msg) {
		this.code = code;
		this.data = data;
		this.msg = msg;
	}

	public HResponse(Integer code, String msg) {
		this.code = code;
		this.msg = msg;
	}

	public HResponse(List<Object> data) {
		this.data = data;
	}

	public HResponse(List<Object> data, Object pageIndex, Object pageSize) {
		this.data = data;
		this.pageIndex = pageIndex;
		this.pageSize = pageSize;
	}

	public HResponse(HException e) {
		this.code = e.getCode();
		this.msg = e.getMessage();
	}
	
	public HResponse() {
		
	}

}
