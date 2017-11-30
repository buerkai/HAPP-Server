package com.happ.db.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.happ.beans.ldy.HLDYMenu;
import com.happ.webcore.db.exception.HDBException;
import com.happ.webcore.db.mybatis.HMybatisDao;

@Repository
public class TestDao extends HMybatisDao {

	@Transactional
	public long insertMenus(Map<String, Object> params ) {
		return insert("insertMenu", params);
	}
	
	
	@Transactional
	public List<Object> queryMenus(Map<String, Object> params) throws HDBException {

		Map<String, Object> map = new HashMap<>();

		

		map.put("name_zh", "Test01");
		map.put("name_tw", "test");
		map.put("name_en", "阿贾克斯的空间卡");
		map.put("enable", Integer.parseInt("1"));
		// map.put("updatetime", HDateFormatUtil.getTimeStamp("yyyy-MM-dd HH:mm:ss"));

		// Map<Object, Object> map2= selectOne("test.queryMenus");
		// if(map2!=null) {
		// return map2;
		// }

		long aa = 0;
//		aa = insert("test.insertMenu", map);
//		 map.put("_where_uuid", 6);
//		aa = update("test.updateMenu", map);

		// aa=delete(HLDYMenu.class, map);
		System.out.println(aa);
		return selectList("test.queryMenus", params, Integer.parseInt(params.get("pageIndex").toString()),
				Integer.parseInt(params.get("pageSize").toString()));
	}

	public List<Object> queryMenus2(Map<String, Object> params) throws HDBException {

		Map<Object, Object> map = new HashMap<>();
		map.put("name_zh", "Test01");
		map.put("name_tw", "test");
		map.put("name_en", "阿贾克斯的空间卡");
		map.put("enable", Integer.parseInt("1"));
		long aa = 0;
		// aa=insert("test.insertMenu", map);
		System.out.println(aa);
		Page<HLDYMenu> page = PageHelper.startPage(Integer.parseInt(params.get("pageIndex").toString()),
				Integer.parseInt(params.get("pageSize").toString()));
		List<Object> ret = sqlSession.selectList("test.queryMenus", params);
		// select("test.queryMenus", params,
		// Long.parseLong(params.get("pageIndex").toString()),
		// Long.parseLong(params.get("pageSize").toString()));
		PageInfo<HLDYMenu> info = new PageInfo<HLDYMenu>(page);
		return ret;
	}

}
