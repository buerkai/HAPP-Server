package com.happ.test.test;

import java.lang.reflect.Method;

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
import com.happ.webcore.base.thread.HTask;

@HApidocMoudle(name = "测试模块")
public class Test01 {

	@Before
	public void setup() {

	}

	@Test
	@HApidocRestfulAPI(name="测试接口", description = "测试接口描述", type = HapidocRestfulAPIType.POST, url = "test02/test02")
	@HApidocParamIn({ @HApidocParam(name = "a", type = HApidocParamType.STRING, description = "参数a", require = true),
			@HApidocParam(name = "b", type = HApidocParamType.STRING, description = "参数b", require = true),
			@HApidocParam(name = "c", type = HApidocParamType.STRING, description = "参数b", require = true), })

	@HApidocParamOut({ @HApidocParam(name = "a", type = HApidocParamType.STRING, description = "参数a", require = true),

	})
	public void test01() throws Exception {

	}

	
	@Test
	@HApidocRestfulAPI(name="测试接口",description = "测试接口描述214123421343245345", type = HapidocRestfulAPIType.POST, url = "test02/test02")
	@HApidocParamIn({ @HApidocParam(name = "a", type = HApidocParamType.STRING, description = "参数a", require = true),
			@HApidocParam(name = "b", type = HApidocParamType.STRING, description = "参数b", require = true),
			@HApidocParam(name = "c", type = HApidocParamType.STRING, description = "参数b", require = true), })

	@HApidocParamOut({ @HApidocParam(name = "a", type = HApidocParamType.STRING, description = "参数a", require = true),

	})
	public void test03() throws Exception {

	}
	
	@Test
	public void test02() {
		
		
		
		HApidoc doc=HApidocFactory.getDocExcel();
		doc.setAuthorInfo("test", "test", "test@qq.com");
		doc.addClass(getClass());
		doc.addClass(Test02.class);
		doc.toFile("c://test.xls");
	}
	
	
	
	@Test
	public void test04() {
		HTask task=new HTask() {
			
			@Override
			public Object doTask(Object param) {
				for(int i=0;i<1000000;i++) {
						System.out.println("i="+i);
				}
				return null;
			}
		}.exe();
		try {
		Thread.sleep(20);
		}catch (Exception e) {
			e.printStackTrace();
		}
		task.cancle();
	}
	

}
