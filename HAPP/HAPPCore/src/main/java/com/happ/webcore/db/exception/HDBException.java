package com.happ.webcore.db.exception;

@SuppressWarnings("serial")
public class HDBException extends RuntimeException {

	public HDBException(Throwable e) {
		super(e);
	}

	public HDBException(String msg) {
		super(msg);
	}

	public HDBException(String msg, Throwable e) {
		super(msg, e);
	}
}
