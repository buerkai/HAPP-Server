package com.happ.webcore.db.mybatis.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/***
 * 自动增长
 * @author Administrator
 *
 */
@Target({ java.lang.annotation.ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface HMybatisIncremental {

	//是否自动增长
	boolean auto() default true;
	
	//oracle序列
	String OracleSequence() default "";
}
