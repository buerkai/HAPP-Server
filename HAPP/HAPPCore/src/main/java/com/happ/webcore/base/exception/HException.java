package com.happ.webcore.base.exception;

@SuppressWarnings("serial")
public class HException extends RuntimeException {

	private int code;
	private String msg;

	public HException(int code, String msg) {
		super(msg);
		this.code = code;
		this.msg = msg;
	}

	public HException() {
		super();
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	@Override
	public String toString() {
		return "{\"code\":" + this.code + ",\"msg\":" + "\"" + msg + "\"}";
	}

}
