package com.happ.test.server;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.happ.server.test.HTest;
import com.happ.server.test.bean.Person;
import com.happ.webcore.base.HResponse;
import com.happ.webcore.base.HServer;
import com.happ.webcore.base.conf.HAPPConfig;


@RestController
@RequestMapping("test")
public class Test01 extends HServer{

	@Autowired
	private HTest moudle;

	
	@RequestMapping(value = "test", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public String Test(String input) {
		log.info("input=" + input);
		log.warn("1111111111111111111111111111111111");
		log.debug("1111111111111111111111111111111111");
		log.error("1111111111111111111111111111111111");
		
		return "this is test....1111";
	}

	@RequestMapping(value = "test", produces = MediaType.APPLICATION_JSON_UTF8_VALUE, method = RequestMethod.GET)
	public String test_get() {
		return "test01"+moudle.getConfig("appurl");
	}

	@RequestMapping(value = "testMap", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public Map<String, String> testMap() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("11", "22");
		map.put("22", "33");
		return map;
	}

	@RequestMapping(value = "testMap2", produces = MediaType.APPLICATION_XML_VALUE)
	public Map<String, String> testMap2() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("11", "22");
		map.put("22", "33");
		return map;
	}

	@RequestMapping(value = "testList", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public List<Object> testList() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("11", "22");
		map.put("22", "33");
		List<Object> list = new ArrayList<Object>();
		list.add(map);
		return list;
	}

	@RequestMapping(value = "testRequst", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public String testRequest(HttpServletRequest request, HttpServletResponse response) {
		StringBuffer sb = new StringBuffer();
		if (request != null) {
			sb.append("request!=null,");
		} else {
			sb.append("request==null,");
		}
		if (response != null) {
			sb.append("response!=null,");
		} else {
			sb.append("response==null,");
		}
		return sb.toString();
	}

	@RequestMapping(value = "testGerPerson", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public Person testBean() {
		Person a = new Person(2, "test", 1);
		return a;
	}

	@RequestMapping(value = "testGetPerson2", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public HResponse testBean2() {
		Person a = new Person(2, "test", 1);
		List<Object> list = new ArrayList<Object>();
		list.add(a);
		return new HResponse(list);
	}

	@RequestMapping(value = "testGetPerson3", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public HResponse testBean3(@RequestBody Person p) {
		System.out.println(p.getName());
		Person a = new Person(2, "test", 1);
		List<Object> list = new ArrayList<Object>();
		list.add(a);
		return new HResponse(list);
	}

	@RequestMapping(value = "test04", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public String test04(@RequestParam(value = "age", required = false, defaultValue = "1") int age,
			@RequestParam(value = "name", required = false, defaultValue = "tom") String name,
			@RequestHeader(value = "Content-Type", required = false) String contentType,
			@CookieValue(value = "JSESSIONID", required = false) String sessionId) {
		return "name=" + name + ",age=" + age + ",Content-Type=" + contentType + ",sessionId=" + sessionId;
	}

	
	
	
	/***
	 * �����쳣
	 * @return
	 */
	@RequestMapping(value = "test05", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public String test05() {
		if(true) {
			throw new RuntimeException("2222222222222");
		}
		return "111";
	}
	
	
	
	/***
	 * ����JSON����
	 * @param map
	 * @return
	 */
	@RequestMapping(value = "test06")
	public HResponse test06(@RequestBody(required=false) Map<String,Object> map) {
		
		List<Object> list=new ArrayList<Object>();
		System.out.println("name="+map);
		list.add(map);
		HResponse response=new HResponse(list);
		return response;
	}
	
	
}
