package com.happ.webcore.spring.servlet;

import org.springframework.web.context.WebApplicationContext;

public class HAppInitListener extends org.springframework.web.context.ContextLoaderListener {
	public HAppInitListener() {
	}

	public HAppInitListener(WebApplicationContext context) {
		super(context);
	}
}
