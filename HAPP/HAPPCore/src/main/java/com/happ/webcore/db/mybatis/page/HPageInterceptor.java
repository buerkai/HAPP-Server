package com.happ.webcore.db.mybatis.page;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.MappedStatement.Builder;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.scripting.defaults.DefaultParameterHandler;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;

import com.happ.webcore.base.log.HLog;
import com.happ.webcore.db.mybatis.util.HMybatisReflectUtil;

@Intercepts({ @Signature(type = Executor.class, method = "query", args = { MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class }),
		@Signature(type = Executor.class, method = "query", args = { MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class, CacheKey.class, BoundSql.class }) })
public class HPageInterceptor implements Interceptor {

	private HLog log = new HLog(getClass());

	@Override
	public Object intercept(Invocation arg0) throws Throwable {
		Object[] args = arg0.getArgs();
		RowBounds rowBounds = (RowBounds) args[2];
		if (!(rowBounds instanceof HPageInfo)) {
			return arg0.proceed();
		}
		HPageInfo pageInfo = (HPageInfo) rowBounds;
		Executor executor = (Executor) arg0.getTarget();
		MappedStatement mappedStatement = (MappedStatement) args[0];
		Object parameterObject = args[1];
		
		BoundSql boundSql = mappedStatement.getBoundSql(parameterObject);
		String sqlStr = boundSql.getSql();

		Connection connection = mappedStatement.getConfiguration().getEnvironment().getDataSource().getConnection();
		long total = queryCount(connection, sqlStr, mappedStatement, parameterObject, boundSql);
		pageInfo.setTotal(total);
		String dbName=connection.getMetaData().getDatabaseProductName().toLowerCase();
		HPageDialect pageDialect=null;;
		if("mysql".equals(dbName)) {
			pageDialect=new HMySQLDialect();
		}else if("oracle".equals(dbName)) {
			
		}else if("sqlserver".equals(dbName)) {
			
		}else if("sqlserver2012".equals(dbName)) {
			
		}
		if(pageDialect!=null) {
			sqlStr=pageDialect.getSQL(pageInfo, sqlStr);
		}
		HMybatisReflectUtil.setFiledValue(boundSql, "sql", sqlStr);
		HSqlSourceWrapper sqlSourceWrapper=new HSqlSourceWrapper(boundSql);
		args[0]=copyFromMappedStatement(mappedStatement, sqlSourceWrapper);
		return arg0.proceed();
	}

	private MappedStatement copyFromMappedStatement(MappedStatement ms, SqlSource newSqlSource) {
		Builder builder = new MappedStatement.Builder(ms.getConfiguration(), ms.getId(), newSqlSource, ms.getSqlCommandType());

		builder.resource(ms.getResource());
		builder.fetchSize(ms.getFetchSize());
		builder.statementType(ms.getStatementType());
		builder.keyGenerator(ms.getKeyGenerator());

		builder.timeout(ms.getTimeout());

		builder.parameterMap(ms.getParameterMap());

		builder.resultMaps(ms.getResultMaps());
		builder.resultSetType(ms.getResultSetType());

		builder.cache(ms.getCache());
		builder.flushCacheRequired(ms.isFlushCacheRequired());
		builder.useCache(ms.isUseCache());

		return builder.build();
	}
	
	private long queryCount(Connection connection, String sqlStr, MappedStatement mappedStatement, Object params, BoundSql boundSql) throws Exception {
		String sql = "SELECT COUNT(1) FROM (" + sqlStr + ") tmptable";
		log.info("查询总数sql:"+sql);
		PreparedStatement preStatement = connection.prepareStatement(sql);
		HMybatisReflectUtil.setFiledValue(boundSql, "sql", sql);
		ParameterHandler parameterHandler = new DefaultParameterHandler(mappedStatement, params, boundSql);
		parameterHandler.setParameters(preStatement);
		ResultSet result = preStatement.executeQuery();
		long count=0;
		if (result.next()) {
			count= result.getLong(1);
		}
		log.info("查询总数:"+count);
		return count;
	}

	@Override
	public Object plugin(Object arg0) {

		return Plugin.wrap(arg0, this);
	}

	@Override
	public void setProperties(Properties arg0) {

	}

}
