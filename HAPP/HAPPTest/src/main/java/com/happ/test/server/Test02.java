package com.happ.test.server;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.happ.db.dao.TestDao;
import com.happ.server.test.HTest;
import com.happ.server.utils.TestFilter;
import com.happ.webcore.base.HResponse;
import com.happ.webcore.base.HServer;
import com.happ.webcore.base.conf.HAPPConstance;
import com.happ.webcore.base.exception.HException;
import com.happ.webcore.base.filter.annotation.HFilterServerBefore;
import com.happ.webcore.db.exception.HDBException;
import com.happ.webcore.db.redis.HRedis;

@RestController
@RequestMapping("test02")
public class Test02 extends HServer {

	@Autowired
	private HTest moudle;

	@Autowired
	private TestDao testDatao;

	@Autowired
	private HRedis redis;

	@HFilterServerBefore(filter = { TestFilter.class })
	@RequestMapping(value = "test01", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public HResponse test01(@RequestBody(required = false) Map<String, Object> params) {
		List<Object> list = new ArrayList<Object>();
		System.out.println("name=" + params);
		list.add(params);
		HResponse response = new HResponse(list);
		return response;
	}

	@RequestMapping(value = "test02", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public HResponse test02(@RequestBody(required = false) Map<String, Object> params) {
		List<Object> list = new ArrayList<Object>();
		if (params == null) {
			params = new HashMap<>();
			params.put(HAPPConstance.PAGE_INDEX, 1);
			params.put(HAPPConstance.PAGE_SIZE, 10);
		}
		try {
			list.addAll(testDatao.queryMenus(params));
		} catch (HDBException e) {
			e.printStackTrace();
		}
		return buildOk(list, params);
	}

	@RequestMapping(value = "test03", produces = { MediaType.APPLICATION_JSON_UTF8_VALUE })
	public HResponse test03(@RequestBody(required = false) JSONObject params) {
		List<Object> list = new ArrayList<Object>();
		if (params == null) {
			params = new JSONObject();
			params.put(HAPPConstance.PAGE_INDEX, 1);
			params.put(HAPPConstance.PAGE_SIZE, 20);
		}
		try {
			list.addAll(testDatao.queryMenus(params.toMap()));
		} catch (HDBException e) {
			e.printStackTrace();
		}
		return buildOk(list, params.toMap());
	}

	@RequestMapping(value = "test04")
	public HResponse test04(@RequestParam(name = "pageIndex", required = false, defaultValue = "1") Integer pageIndex,
			@RequestParam(name = "pageSize", required = false, defaultValue = "10") Integer pageSize) {
		List<Object> list = new ArrayList<Object>();
		Map<String, Object> params = new HashMap<>();
		params.put(HAPPConstance.PAGE_INDEX, pageIndex);
		params.put(HAPPConstance.PAGE_SIZE, pageSize);
		try {
			list.addAll(testDatao.queryMenus(params));
		} catch (HDBException e) {
			e.printStackTrace();
		}
		return buildOk(list, params);
	}

	@RequestMapping(value = "test05")
	public HResponse test05() {
		List<Object> list = new ArrayList<Object>();
		Map<String, Object> params = new HashMap<>();
		params.put(HAPPConstance.PAGE_INDEX, 1);
		params.put(HAPPConstance.PAGE_SIZE, 10);
		redis.opsForValue().set("22", "111");
		return buildOk(list, params);
	}

	@RequestMapping(value="addMenu")
	public HResponse test06() {
		Map<String, Object> map = new HashMap<>();

		map.put("name_zh", "Test01");
		map.put("name_tw", "test");
		map.put("name_en", "阿贾克斯的空间卡");
		map.put("enable", Integer.parseInt("1"));
		try {
			testDatao.insertMenus(map);
			Object id = map.get("uuid");
			map.clear();
			map.put("uuid", id);
			List<Object> data = new ArrayList<>();
			data.add(map);
			return buildOk(data);
		} catch (Exception e) {
			throw new HException(-2, e.getMessage());
		}
	}
}
