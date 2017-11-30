package com.happ.webcore.base.conf;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class HConfigManagerFactory {

	private HConfigHelper messageHelper;
	private HConfigHelper configHelper;

	@Autowired(required=true) @Qualifier("happProperties")
	private HAPPConfig config;

	@PostConstruct
	private void init() {
		if ("1".equals(config.getProperty(HAPPConstance.CONFIG_app_useRedis, "0")) && "1".equals(config.getProperty(HAPPConstance.CONFIG_app_configSaveInRedis, "0"))) {
			// redis存储
			messageHelper = new HConfigRedisHelper();
			messageHelper.setType("message");
			messageHelper.setPath(config.getProperty(HAPPConstance.CONFIG_app_configSaveInRedis_dbId));
			configHelper = new HConfigRedisHelper();
			configHelper.setType("config");
			configHelper.setPath(config.getProperty(HAPPConstance.CONFIG_app_configSaveInRedis_dbId));
		} else {
			String path = config.getProperty(HAPPConstance.CONFIG_app_configPath);
			if(path==null || path.isEmpty()) {
				path=this.getClass().getResource("/").getPath()+"/configs";
			}
			
			// 文件存储
			messageHelper = new HConfigFileHelper();
			messageHelper.setType("message");
			messageHelper.setPath(path);
			configHelper = new HConfigFileHelper();
			configHelper.setType("config");
			configHelper.setPath(path);

		}
	}

	/***
	 * 获取消息配置
	 * 
	 * @return
	 */
	public HConfigHelper getMessageConfig() {
		return messageHelper;
	}

	/***
	 * 获取配置信息配置
	 * 
	 * @return
	 */
	public HConfigHelper getConfigConfig() {
		return configHelper;
	}

}
