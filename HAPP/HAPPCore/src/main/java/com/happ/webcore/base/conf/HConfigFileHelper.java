package com.happ.webcore.base.conf;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Properties;
import java.util.Set;
import java.util.WeakHashMap;

/***
 * 配置信息管理工具类
 * 
 * @author Administrator
 *
 */
public class HConfigFileHelper implements HConfigHelper {

	private WeakHashMap<String, String> memoryData = new WeakHashMap<String, String>();

	private HashMap<String, Properties> saveData = new HashMap<String, Properties>();

	protected String type = "";

	protected String configPath = "";


	/***
	 * 根据模块名获取配置文件存储位置
	 * 
	 * @param moduleName
	 * @return
	 */
	protected File getFile(String moduleName) {
		String rootPath = configPath;
		File rootFile = new File(rootPath,moduleName);
		if (!rootFile.exists()) {
			// 如果创建文件不存在
			if (!rootFile.mkdirs()) {
				rootFile = new File(".");
			}
		}
		File msgFile = new File(rootFile, type + ".txt");
		return msgFile;
	}

	/***
	 * 
	 * @param moduleName
	 * @return
	 */
	public Properties readFromFile2Properties(String moduleName) {
		Properties pop = new Properties();
		try {
			File msgFile = getFile(moduleName);
			if (msgFile == null || !msgFile.exists()) {
				pop.clear();
				pop = null;
				return null;
			}
			pop.load(new FileInputStream(msgFile));
			Set<Object> keys = pop.keySet();
			for (Object itemKey : keys) {
				String itemValue = pop.getProperty(itemKey.toString());
				if (itemValue != null) {
					memoryData.put(itemKey.toString(), itemValue.toString());
				}
			}
			keys = null;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return pop;
	}

	/***
	 * 读取异常信息
	 * 
	 * @param moduleName
	 */
	public void readFromFile(String moduleName) {
		Properties pop = readFromFile2Properties(moduleName);
		if (pop == null) {
			return;
		}
		Set<Object> keys = pop.keySet();
		for (Object itemKey : keys) {
			Object itemValue = pop.get(itemKey);
			if (itemValue != null) {
				memoryData.put(itemKey.toString(), itemValue.toString());
			}
		}
		pop.clear();
		pop = null;
	}

	@Override
	public boolean save(String moduleName) {
		Properties tmpPop = saveData.remove(moduleName);
		if (tmpPop == null) {
			return false;
		}
		try {
			tmpPop.store(new FileOutputStream(getFile(moduleName)), "update " + moduleName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		tmpPop.clear();
		tmpPop = null;
		return true;
	}

	@Override
	public void add(String moduleName, String key, String value) {
		memoryData.put(key, value);
		Properties tmpPop = saveData.get(moduleName);
		if (tmpPop == null) {
			tmpPop=readFromFile2Properties(moduleName);
			if(tmpPop==null) {
				tmpPop = new Properties();
			}
			saveData.put(moduleName, tmpPop);
		}
		if (!tmpPop.containsKey(key)) {
			tmpPop.put(key, value);
		}
	}

	@Override
	public String get(String moduleName, String key) {
		String msg = memoryData.get(key);
		if (msg == null) {
			// 读取文件
			readFromFile(moduleName);
		}
		msg = memoryData.get(key);
		return msg;
	}

	@Override
	public void setType(String type) {
		this.type=type;
	}

	@Override
	public void setPath(String path) {
		this.configPath=path;
	}
}
