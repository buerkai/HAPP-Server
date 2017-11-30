package com.happ.webcore.base.conf;

import com.happ.webcore.base.exception.HException;

public class HAPPConstance {
	
	
	public static final String CONFIG_app_useRedis="app.useRedis";
	public static final String CONFIG_app_redis_ip="app.redis.ip";
	public static final String CONFIG_app_redis_port="app.redis.port";
	public static final String CONFIG_app_redis_pass="app.redis.pass";
	
	public static final String CONFIG_app_configPath="app.configPath";

	public static final String CONFIG_app_configSaveInRedis="app.configSaveInRedis";
	public static final String CONFIG_app_configSaveInRedis_dbId="app.configSaveInRedis.dbId";
	
	
	public static final String CONFIG_app_ehcache="app.ehcache";
	public static final String CONFIG_app_ehcache_path="app.ehcache.path";
	public static final String CONFIG_app_ehcache_name="app.ehcache.name";
	public static final String CONFIG_app_ehcache_maxElementsInMemory="app.ehcache.maxElementsInMemory";
	public static final String CONFIG_app_ehcache_maxMBInMemory="app.ehcache.maxMBInMemory";
	public static final String CONFIG_app_ehcache_maxMBOnDisk="app.ehcache.maxMBOnDisk";

	public static final String UTF8 = "utf-8";
	
	public static final String PAGE_INDEX="pageIndex";
	
	public static final String PAGE_SIZE="pageSize";
	
	public static final String PAGE_TOTAL="pageTotal";

	public static final String CONTENT_TYPE_JSON = "application/json";
	
	public static final String DB_Mybatis_autoSql_BeanClass="__mybatisAutoSqlBeanClass__";

	public static final HException ERROR_UNKNOW = new HException(-1, "未知异常");
	
	public static final HException ERROR_NO_INTERFACE = new HException(-2, "未知接口");
	
	public static final HException ERROR_SYSTEM = new HException(-3, "系统未知错误");
}
