package com.happ.webcore.db.mybatis.mapper;

import java.util.Map;

import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Options.FlushCachePolicy;
import org.apache.ibatis.annotations.UpdateProvider;
import org.json.JSONObject;

import com.happ.webcore.db.mybatis.HDBSqlProvider;


@CacheNamespace
public interface HDBMapper  {

	@Options(flushCache = FlushCachePolicy.TRUE, useGeneratedKeys = true, keyProperty = "uuid")
	@InsertProvider(type = HDBSqlProvider.class, method = "insert")
	public long insert(Object entity);

	@Options(flushCache = FlushCachePolicy.TRUE)
	@UpdateProvider(type = HDBSqlProvider.class, method = "update")
	public int update(Object entity);

	@Options(flushCache = FlushCachePolicy.TRUE)
	@DeleteProvider(type = HDBSqlProvider.class, method = "delete")
	public int delete(Object entity);

	@Options(flushCache = FlushCachePolicy.TRUE, useGeneratedKeys = true, keyProperty = "uuid")
	@InsertProvider(type = HDBSqlProvider.class, method = "insertMap")
	public long insertMap(Map<?, ?> params);

	@Options(flushCache = FlushCachePolicy.TRUE)
	@UpdateProvider(type = HDBSqlProvider.class, method = "updateMap")
	public int updateMap(Map<?, ?> params);

	@Options(flushCache = FlushCachePolicy.TRUE)
	@DeleteProvider(type = HDBSqlProvider.class, method = "deleteMap")
	public int deleteMap(Map<?, ?> params);

	
}
