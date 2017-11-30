package com.happ.webcore.base.conf;


import com.happ.webcore.db.redis.HRedis;

public class HConfigRedisHelper  implements HConfigHelper{
	
	private String type="";
	
	@Override
	public void add(String moduleName, String key, String msg) {
		HRedis.getInstance().opsForValue().set(type+":"+moduleName+":"+key, msg);
	}

	@Override
	public String get(String moduleName, String key) {
		return HRedis.getInstance().opsForValue().get(type+":"+moduleName+":"+key);
	}

	@Override
	public boolean save(String moduleName) {
		return true;
	}

	@Override
	public void setType(String type) {
		this.type=type;
	}

	@Override
	public void setPath(String path) {
		
	}

	

}
