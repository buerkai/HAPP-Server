package com.happ.test.test;


import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

import com.happ.webcore.apidoc.HApidoc;
import com.happ.webcore.apidoc.HApidocFactory;
import com.happ.webcore.apidoc.HApidocParamType;
import com.happ.webcore.apidoc.HapidocRestfulAPIType;
import com.happ.webcore.apidoc.annotation.HApidocMoudle;
import com.happ.webcore.apidoc.annotation.HApidocParam;
import com.happ.webcore.apidoc.annotation.HApidocParamIn;
import com.happ.webcore.apidoc.annotation.HApidocParamOut;
import com.happ.webcore.apidoc.annotation.HApidocRestfulAPI;

@HApidocMoudle(name = "测试模块02")
public class Test02 {

	@Before
	public void setup() {

	}

	@Test
	@HApidocRestfulAPI(name="测试接口",description = "测试接口描述000000000", type = HapidocRestfulAPIType.POST, url = "test02/test02")
	@HApidocParamIn({ @HApidocParam(name = "a", type = HApidocParamType.STRING, description = "参数a", require = true),
			@HApidocParam(name = "b", type = HApidocParamType.STRING, description = "参数b", require = true),
			@HApidocParam(name = "c", type = HApidocParamType.STRING, description = "参数b", require = true), })

	@HApidocParamOut({ @HApidocParam(name = "a", type = HApidocParamType.STRING, description = "参数a", require = true),

	})
	public void test01() throws Exception {

	}

	@Test
	public void test02() {
		
		
		
		HApidoc doc=HApidocFactory.getDocWord();
		doc.setAuthorInfo("test", "test", "test@qq.com");
		doc.addClass(getClass());
		doc.addClass(Test01.class);
		//doc.toFile("c://test.xls");
		
		doc.toFile("c://test.docx");
	}
	
	@Test
	public void test03() {
		HApidoc doc=HApidocFactory.getDocExcel();
		doc.setAuthorInfo("test", "test", "test@qq.com");
		doc.addClass(getClass());
		doc.addClass(Test01.class);
		doc.toFile("c://test.xls");
//		JSONObject detail=new JSONObject();
//		detail.put("content", "asdfasdfasdf");
//		detail.put("extraData", "aasdfasdfasdf");
//		
//		
//		JSONObject metaData=new JSONObject();
//		metaData.put("msgType", "COMM");
//		metaData.put("pageId", "1");
//		metaData.put("positionId", "1asdfasdf");
//		
//		JSONObject o=new JSONObject();
//		o.put("title", "test1");
//		o.put("desc", "测试描述");
//		o.put("detail", detail.toString());
//		o.put("metaData", metaData.toString());
//		o.put("showFlag", "Y");
//		o.put("fromUser", "11");
//		o.put("toUser", "80252517");
//		o.put("pkgName", "com.xx.xx.xx");
//		
//		System.out.println(o.toString());
	}
	
	@Test
	public void test04() {
		HApidoc doc=HApidocFactory.getDocPdf();
		doc.setAuthorInfo("test", "test", "test@qq.com");
		doc.addClass(getClass());
		doc.addClass(Test01.class);
		doc.addClass(getClass());
		doc.addClass(Test01.class);
		doc.addClass(getClass());
		doc.addClass(Test01.class);
		doc.toFile("c://test.pdf");
	}

}
