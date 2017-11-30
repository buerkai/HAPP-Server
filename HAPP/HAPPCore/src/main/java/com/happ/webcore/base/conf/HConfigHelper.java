package com.happ.webcore.base.conf;

public interface HConfigHelper {

	/***
	 * 添加数据
	 * 
	 * @param moduleName
	 * @param key
	 * @param value
	 */
	void add(String moduleName, String key, String value);

	/***
	 * 获取数据
	 * 
	 * @param moduleName
	 * @param key
	 * @return
	 */
	String get(String moduleName, String key);

	/***
	 * 保存数据
	 * 
	 * @param moduleName
	 * @return
	 */
	boolean save(String moduleName);

	/***
	 * 设置配置类型
	 * 
	 * @param type
	 */
	void setType(String type);

	/***
	 * 设置路径
	 * 
	 * @param path
	 */
	void setPath(String path);

}
