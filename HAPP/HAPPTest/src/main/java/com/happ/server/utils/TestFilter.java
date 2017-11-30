package com.happ.server.utils;

import com.happ.webcore.base.exception.HException;
import com.happ.webcore.base.filter.HFilterServerBefore;

public class TestFilter implements HFilterServerBefore {

	@Override
	public boolean doFilter(Object... params) {
		System.out.println("p count=" + params.length);
		if (false) {
			throw new HException(-2, "1111111111");
		}
		return false;
	}

}
