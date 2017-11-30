package com.happ.webcore.base;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;

import com.happ.webcore.base.conf.HAPPConstance;
import com.happ.webcore.base.conf.HConfigManagerFactory;
import com.happ.webcore.base.exception.HException;

public abstract class HModule implements InitializingBean{

	private String moduleName = getClass().getSimpleName();

	
	
	@Autowired
	HConfigManagerFactory mamager;
	
	@Override
	public void afterPropertiesSet() throws Exception {
		
	}
	
	@PostConstruct
	private void init() {
		initConfig();
		initMessage();
		mamager.getMessageConfig().save(moduleName);
		mamager.getConfigConfig().save(moduleName);
	}
	/***
	 * 初始化错误信息
	 */
	public abstract void initMessage();

	/***
	 * 初始化配置信息
	 */
	public abstract void initConfig();

	/***
	 * 添加异常信息
	 * 
	 * @param code
	 * @param msg
	 */
	public void addMessage(Integer code, String msg) {
		mamager.getMessageConfig().add(moduleName, code.toString(), msg);
	}

	/***
	 * 获取异常信息
	 * 
	 * @param code
	 * @return
	 */
	public HException getMesssage(Integer code) {
		String value =mamager.getMessageConfig().get(moduleName, code.toString());
		if (value != null) {
			return new HException(code, value);
		} else {
			return HAPPConstance.ERROR_UNKNOW;
		}
	}

	/***
	 * 添加配置信息
	 * 
	 * @param key
	 * @param value
	 */
	public void addConfig(String key, String value) {
		mamager.getConfigConfig().add(moduleName, key, value);
	}

	/***
	 * 获取配置信息
	 * 
	 * @param key
	 * @return
	 */
	public String getConfig(String key) {
		return mamager.getConfigConfig().get(moduleName, key);
	}
}
