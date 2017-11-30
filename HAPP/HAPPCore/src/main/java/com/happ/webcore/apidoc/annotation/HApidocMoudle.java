package com.happ.webcore.apidoc.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ java.lang.annotation.ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface HApidocMoudle {

	// 描述
	String name() default "";

	// 排序,值越小越在前面
	int order() default 0;
}
