package com.happ.webcore.db.mybatis.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/***
 * 字段名称
 * 
 * @author Administrator
 *
 */
@Target({ java.lang.annotation.ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface HMybatisColumn {
	// 表名称
	String value();
}
