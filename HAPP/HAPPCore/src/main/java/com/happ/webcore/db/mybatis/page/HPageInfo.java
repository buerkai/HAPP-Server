package com.happ.webcore.db.mybatis.page;

import org.apache.ibatis.session.RowBounds;

public class HPageInfo extends RowBounds {

	private long pageIndex;
	private long pageSize;
	private long total;

	public HPageInfo(long pageIndex, long pageSize) {
		if (pageIndex < 1) {
			this.pageIndex = 1;
		} else {
			this.pageIndex = pageIndex;
		}
		if (pageSize < 1) {
			this.pageSize = 1;
		} else {
			this.pageSize = pageSize;
		}

	}

	@Override
	public int getOffset() {
		return (int)((pageIndex - 1) * pageSize);
	}

	@Override
	public int getLimit() {
		return (int)pageSize;
	}

	/***
	 * 获取page起始页
	 * 
	 * @return
	 */
	public long getPageStart() {
		return (pageIndex - 1) * pageSize;
	}

	/***
	 * 获取page结束页
	 * 
	 * @return
	 */
	public long getPageEnd() {
		return pageIndex * pageSize;
	}

	public long getPageIndex() {
		return pageIndex;
	}

	public void setPageIndex(long pageIndex) {
		this.pageIndex = pageIndex;
	}

	public long getPageSize() {
		return pageSize;
	}

	public void setPageSize(long pageSize) {
		this.pageSize = pageSize;
	}

	public long getTotal() {
		return total;
	}

	public void setTotal(long total) {
		this.total = total;
	}

}
