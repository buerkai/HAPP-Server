package com.happ.webcore.db.mybatis.page;

import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.SqlSource;

public class HSqlSourceWrapper implements SqlSource {

	private BoundSql boundSql;
	public HSqlSourceWrapper(BoundSql boundSql) {
		this.boundSql=boundSql;
	}
	
	@Override
	public BoundSql getBoundSql(Object paramObject) {
		return boundSql;
	}

}
