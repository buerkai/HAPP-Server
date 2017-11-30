package com.happ.webcore.apidoc.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.happ.webcore.apidoc.HapidocRestfulAPIType;

@Target({ java.lang.annotation.ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
public @interface HApidocRestfulAPI {
   
	String name();
	
	// 地址
	String url() default "";

	// 描述
	String description() default "";

	// 请求类型
	HapidocRestfulAPIType type() default HapidocRestfulAPIType.POST;

	// 排序,值越小越在前面
	int order() default 0;
}
