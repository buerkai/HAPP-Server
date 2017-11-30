package com.happ.webcore.base.conf;

import java.util.Properties;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

public class HAPPConfig extends PropertyPlaceholderConfigurer {

	private Properties props;

	private static HAPPConfig instance;

	public HAPPConfig() {
		instance=this;
	}
	
	
	public static HAPPConfig getInstance() {
		return instance;
	}

	@Override
	protected void processProperties(ConfigurableListableBeanFactory beanFactoryToProcess, Properties props)
			throws BeansException {
		super.processProperties(beanFactoryToProcess, props);
		this.props = props;
	}

	public String getProperty(String key) {
		return this.props.getProperty(key);
	}

	public String getProperty(String key, String defaultValue) {
		return this.props.getProperty(key, defaultValue);
	}

	public Object setProperty(String key, String value) {
		return this.props.setProperty(key, value);
	}

	public long getProperty(String key, long defaultValue) {
		String v = props.getProperty(key);
		if (v == null || v.isEmpty()) {
			return defaultValue;
		}
		try {
			return Long.parseLong(v);
		} catch (Exception e) {
			return defaultValue;
		}
	}

	public float getProperty(String key, float defaultValue) {
		String v = props.getProperty(key);
		if (v == null || v.isEmpty()) {
			return defaultValue;
		}
		try {
			return Float.parseFloat(v);
		} catch (Exception e) {
			return defaultValue;
		}
	}

	public int getProperty(String key, int defaultValue) {
		String v = props.getProperty(key);
		if (v == null || v.isEmpty()) {
			return defaultValue;
		}
		try {
			return Integer.parseInt(v);
		} catch (Exception e) {
			return defaultValue;
		}
	}
}
