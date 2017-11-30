package com.happ.webcore.db.redis;


import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.converter.HttpMessageConverter;

import com.happ.webcore.base.conf.HAPPConfig;
import com.happ.webcore.base.conf.HAPPConstance;

import redis.clients.jedis.JedisPoolConfig;

public class HRedis extends StringRedisTemplate {

	private JedisConnectionFactory factory = null;
	
	public JedisConnectionFactory getFactory() {

		return factory;
	}
	
	
	public static HRedis redis;

	
	public static HRedis getInstance() {
		return redis;
	}
	
	@Override
	public void afterPropertiesSet() {
		JedisPoolConfig config=new JedisPoolConfig();
		//连接耗尽时是否阻塞, false报异常,ture阻塞直到超时, 默认true
		config.setBlockWhenExhausted(true);
		 
		//设置的逐出策略类名, 默认DefaultEvictionPolicy(当连接超过最大空闲时间,或连接数超过最大空闲连接数)
		config.setEvictionPolicyClassName("org.apache.commons.pool2.impl.DefaultEvictionPolicy");

		//是否启用pool的jmx管理功能, 默认true
		config.setJmxEnabled(true);
		 
		//MBean ObjectName = new ObjectName("org.apache.commons.pool2:type=GenericObjectPool,name=" + "pool" + i); 默 认为"pool", JMX不熟,具体不知道是干啥的...默认就好.
		config.setJmxNamePrefix("pool");
		 
		//是否启用后进先出, 默认true
		config.setLifo(true);
		 
		//最大空闲连接数, 默认8个
		config.setMaxIdle(8);
		 
		//最大连接数, 默认8个
		config.setMaxTotal(16);
		 
		//获取连接时的最大等待毫秒数(如果设置为阻塞时BlockWhenExhausted),如果超时就抛异常, 小于零:阻塞不确定的时间,  默认-1
		config.setMaxWaitMillis(-1);
		 
		//逐出连接的最小空闲时间 默认1800000毫秒(30分钟)
		config.setMinEvictableIdleTimeMillis(1800000);
		 
		//最小空闲连接数, 默认0
		config.setMinIdle(0);
		 
		//每次逐出检查时 逐出的最大数目 如果为负数就是 : 1/abs(n), 默认3
		config.setNumTestsPerEvictionRun(3);
		 
		//对象空闲多久后逐出, 当空闲时间>该值 且 空闲连接>最大空闲数 时直接逐出,不再根据MinEvictableIdleTimeMillis判断  (默认逐出策略)   
		config.setSoftMinEvictableIdleTimeMillis(1800000);
		 
		//在获取连接的时候检查有效性, 默认false
		config.setTestOnBorrow(false);
		 
		//在空闲时检查有效性, 默认false
		config.setTestWhileIdle(false);
		 
		//逐出扫描的时间间隔(毫秒) 如果为负数,则不运行逐出线程, 默认-1
		config.setTimeBetweenEvictionRunsMillis(-1);
		
	
		
		factory=new JedisConnectionFactory(config);
		factory.setHostName(HAPPConfig.getInstance().getProperty(HAPPConstance.CONFIG_app_redis_ip, "127.0.0.1"));
		factory.setPort(HAPPConfig.getInstance().getProperty(HAPPConstance.CONFIG_app_redis_port, 6379));
		String password = HAPPConfig.getInstance().getProperty(HAPPConstance.CONFIG_app_redis_pass);
		if (password != null || !password.isEmpty()) {
			factory.setPassword(password);
		}
		setConnectionFactory(factory);
		super.afterPropertiesSet();
		redis=this;
	}

}
