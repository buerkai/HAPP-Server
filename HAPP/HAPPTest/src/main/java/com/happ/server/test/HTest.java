package com.happ.server.test;

import org.springframework.stereotype.Component;

import com.happ.webcore.base.HModule;

@Component
public class HTest extends HModule {

	@Override
	public void initConfig() {
		addConfig("appurl", "http://www.baidu.com");
	}

	@Override
	public void initMessage() {
		addMessage(20, "this is test");

	}
}
