package com.happ.webcore.base.log;

public class HLog {

	private org.apache.commons.logging.Log log;

	public HLog(String className) {
		log = org.apache.commons.logging.LogFactory.getLog(className);
	}

	public HLog(Class<?> classz) {
		log = org.apache.commons.logging.LogFactory.getLog(classz);
	}

	public void debug(Object paramObject) {
		log.debug(paramObject);
	}

	public void debug(Object paramObject, Throwable paramThrowable) {
		log.debug(paramObject, paramThrowable);
	}

	public void error(Object paramObject) {
		log.error(paramObject);
	}

	public void error(Object paramObject, Throwable paramThrowable) {
		log.error(paramObject, paramThrowable);
	}

	public void fatal(Object paramObject) {
		log.fatal(paramObject);
	}

	public void fatal(Object paramObject, Throwable paramThrowable) {
		log.fatal(paramObject, paramThrowable);
	}

	public void info(Object paramObject) {
		log.info(paramObject);
	}

	public void info(Object paramObject, Throwable paramThrowable) {
		log.info(paramObject, paramThrowable);
	}

	public boolean isDebugEnabled() {
		return log.isDebugEnabled();
	}

	public boolean isErrorEnabled() {
		return log.isErrorEnabled();
	}

	public boolean isFatalEnabled() {
		return log.isFatalEnabled();
	}

	public boolean isInfoEnabled() {
		return log.isInfoEnabled();
	}

	public boolean isTraceEnabled() {
		return log.isTraceEnabled();
	}

	public boolean isWarnEnabled() {
		return log.isWarnEnabled();
	}

	public void trace(Object paramObject) {
		log.trace(paramObject);
	}

	public void trace(Object paramObject, Throwable paramThrowable) {
		log.trace(paramObject, paramThrowable);
	}

	public void warn(Object paramObject) {
		log.warn(paramObject);
	}

	public void warn(Object paramObject, Throwable paramThrowable) {
		log.warn(paramObject, paramThrowable);
	}

}
