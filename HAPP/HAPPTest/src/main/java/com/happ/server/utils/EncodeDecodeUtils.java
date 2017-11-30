package com.happ.server.utils;

import org.json.JSONObject;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("utilEncodDecode")
@RestController
public class EncodeDecodeUtils {

	
	@RequestMapping(value="test",produces=org.springframework.http.MediaType.APPLICATION_JSON_VALUE)
	public String test() {
		JSONObject test=new JSONObject();
		test.put("11", "111").put("22", "22");
		return test.toString();
	}
	
}
