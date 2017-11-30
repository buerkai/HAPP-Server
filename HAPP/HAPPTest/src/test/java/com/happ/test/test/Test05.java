package com.happ.test.test;

import java.io.File;
import java.util.concurrent.TimeUnit;

import org.ehcache.Cache;
import org.ehcache.CacheManager;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.CacheManagerBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.ehcache.config.units.EntryUnit;
import org.ehcache.config.units.MemoryUnit;
import org.ehcache.expiry.Duration;
import org.ehcache.expiry.Expirations;
import org.json.JSONObject;
import org.junit.Test;

import com.happ.webcore.base.conf.HAPPConstance;

public class Test05 {

	@Test
	public void test01() {
		CacheManager cacheManager = CacheManagerBuilder.newCacheManagerBuilder()
				.withCache("aa",
						CacheConfigurationBuilder.newCacheConfigurationBuilder(Object.class, Object.class,
								ResourcePoolsBuilder.heap(10)))
				.with(CacheManagerBuilder.persistence(new File("c://test"))).build();
		cacheManager.init();

		Cache<Object, Object> preConfigured = cacheManager.getCache("aa", Object.class, Object.class);

		Cache<Object, Object> myCache = cacheManager.createCache("myCache", CacheConfigurationBuilder
				.newCacheConfigurationBuilder(Object.class, Object.class, ResourcePoolsBuilder.heap(10)));

		myCache.put(1L, "da one!");
		String value = myCache.get(1L).toString();

		cacheManager.removeCache("preConfigured");

		cacheManager.close();
	}

	@Test
	public void test02() {
		String name = "test";
		try {
			
			CacheManager cacheManager = CacheManagerBuilder.newCacheManagerBuilder()
					.with(CacheManagerBuilder.persistence(new File("c://test")))
					.withCache(name,
							CacheConfigurationBuilder.newCacheConfigurationBuilder(String.class, Object.class,
									ResourcePoolsBuilder.newResourcePoolsBuilder()

											.heap(8000, EntryUnit.ENTRIES)

											.offheap(2, MemoryUnit.MB)

											.disk(20, MemoryUnit.MB, true)

							).withExpiry(Expirations.timeToLiveExpiration(Duration.of(20, TimeUnit.SECONDS)))

					).build(true);

			Cache<String, Object> preConfigured = cacheManager.getCache(name, String.class, Object.class);
			preConfigured.put("aa", "da one!");
			String value = preConfigured.get("aa").toString();
			System.out.println(value);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void test03() {
		JSONObject a=new JSONObject();
		a.put("a","22");
		String c=a.toString().replaceAll("\"", "##");
		System.out.println(c);
	}
	
	
	@Test
	public void test04() {
		String a="a.png";
		int index=a.indexOf(".");
		if(index>0) {
			a=a.substring(0,index)+"_ccc"+a.substring(index);
		}
		System.out.println(a);
	}
}
