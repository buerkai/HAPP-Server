package com.happ.webcore.db.mybatis.page;

public interface HPageDialect {

	
	String getSQL(HPageInfo pageInfo,String sql);
}
