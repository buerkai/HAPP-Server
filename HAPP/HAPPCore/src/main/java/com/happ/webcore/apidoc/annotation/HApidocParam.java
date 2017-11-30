package com.happ.webcore.apidoc.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.happ.webcore.apidoc.HApidocParamType;

@Target({ java.lang.annotation.ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
public @interface HApidocParam {

	// 参数名称
	String name();

	// 描述
	String description() default "";

	// 是否必须
	boolean require() default false;

	// 参数类型
	HApidocParamType type() default HApidocParamType.STRING;

	// 参数长度
	int length() default 0;

	// 排序,值越小越在前面
	int order() default 0;

}
