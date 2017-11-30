package com.happ.webcore.db.mybatis.page;

public class HMySQLDialect implements HPageDialect {

	@Override
	public String getSQL(HPageInfo pageInfo, String sql) {
		return sql + " limit " + pageInfo.getPageStart() + "," + pageInfo.getPageSize();
	}

}
