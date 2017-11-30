package com.happ.webcore.base.filter.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ java.lang.annotation.ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
public @interface HFilterServerAfter {

	String value() default "";

	Class<? extends com.happ.webcore.base.filter.HFilterServerAfter>[] filter();
}
