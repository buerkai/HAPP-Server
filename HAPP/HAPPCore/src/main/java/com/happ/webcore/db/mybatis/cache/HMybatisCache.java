package com.happ.webcore.db.mybatis.cache;

//import java.io.File;
//import java.io.Serializable;
//import java.util.concurrent.TimeUnit;
//import java.util.concurrent.locks.ReadWriteLock;
//
//import org.apache.ibatis.cache.Cache;
//import org.ehcache.CacheManager;
//import org.ehcache.config.builders.CacheConfigurationBuilder;
//import org.ehcache.config.builders.CacheManagerBuilder;
//import org.ehcache.config.builders.ResourcePoolsBuilder;
//import org.ehcache.config.units.EntryUnit;
//import org.ehcache.config.units.MemoryUnit;
//import org.ehcache.expiry.Duration;
//import org.ehcache.expiry.Expirations;
//
//import com.happ.webcore.base.conf.HAPPConfig;
//import com.happ.webcore.base.conf.HAPPConstance;
//
//import net.sf.ehcache.Element;
//
//public class HMybatisCache implements Cache {
//
//	private CacheManager ehcacheManager;
//
//	private String id;
//
//	private org.ehcache.Cache<Serializable, Element> cache;
//
//	private HAPPConfig config;
//
//	private boolean userCache;
//
//	public HMybatisCache(String id) {
//		config = HAPPConfig.getInstance();
//		this.id = id;
//		userCache = "1".equals(config.getProperty(HAPPConstance.CONFIG_app_ehcache, "0"));
//		if (!userCache) {
//			return;
//		}
//		// 设置缓存信息
//		String name = config.getProperty(HAPPConstance.CONFIG_app_ehcache_name);
//		if (name == null || name.isEmpty()) {
//			name = "ehcache";
//		}
//
//		String defaultPath = this.getClass().getResource("/").getPath() + "ehcache";
//		ehcacheManager = CacheManagerBuilder.newCacheManagerBuilder()
//				.with(CacheManagerBuilder
//						.persistence(new File(config.getProperty(HAPPConstance.CONFIG_app_ehcache_path, defaultPath))))
//				.withCache(name, CacheConfigurationBuilder.newCacheConfigurationBuilder(Serializable.class, Element.class,
//						ResourcePoolsBuilder.newResourcePoolsBuilder()
//
//								.heap(config.getProperty(HAPPConstance.CONFIG_app_ehcache_maxElementsInMemory, 8000),
//										EntryUnit.ENTRIES)
//
//								.disk(config.getProperty(HAPPConstance.CONFIG_app_ehcache_maxMBOnDisk, 20),
//										MemoryUnit.MB, true)
//
//				).withExpiry(Expirations.timeToLiveExpiration(Duration.of(20, TimeUnit.SECONDS)))
//						
//						).build(true);
//		cache = ehcacheManager.getCache(name, Serializable.class, Element.class);
//	}
//
//	@Override
//	public String getId() {
//		return this.id;
//	}
//
//	@Override
//	public void putObject(Object key, Object value) {
//		if (!userCache) {
//			return;
//		}
//		Element e=new Element(key, value);
//		cache.put(e.getKey(), e);
//		
//	}
//
//	@Override
//	public Object getObject(Object key) {
//		if (!userCache) {
//			return null;
//		}
//		Element e=new Element(key, "");
//		Element value = cache.get(e.getKey());
//		if(value==null) {
//			return null;
//		}
//		return value.getObjectValue();
//	}
//
//	@Override
//	public Object removeObject(Object key) {
//		if (!userCache) {
//			return null;
//		}
//		Element e=new Element(key, "");
//		Element value = cache.get(e.getKey());
//		cache.remove(e.getKey());
//		if(value==null) {
//			return null;
//		}
//		return value.getObjectValue();
//	}
//
//	@Override
//	public void clear() {
//		if (!userCache) {
//			return;
//		}
//		cache.clear();
//	}
//
//	@Override
//	public int getSize() {
//		if (!userCache) {
//			return 0;
//		}
//		return 0;
//	}
//
//	@Override
//	public ReadWriteLock getReadWriteLock() {
//		return null;
//	}
//
//}
