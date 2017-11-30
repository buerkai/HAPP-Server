package com.happ.webcore.db.mybatis.util;

import java.lang.reflect.Field;
import java.util.Date;

import com.happ.webcore.base.utils.HDateFormatUtil;
import com.happ.webcore.db.mybatis.annotation.HMybatisTable;

public class HMybatisReflectUtil {

	/***
	 * 获取表名称
	 * 
	 * @param classz
	 * @return
	 */
	public static String getTableName(Class<?> classz) {
		HMybatisTable table = classz.getDeclaredAnnotation(HMybatisTable.class);
		if (table != null) {
			return table.value();
		}
		return classz.getSimpleName();
	}

	/***
	 * 获取值
	 * 
	 * @param field
	 * @param target
	 * @return
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	public static Object getFiledValue(Field field, Object target) throws IllegalArgumentException, IllegalAccessException {
		Class<?> types = field.getType();
		Object obj = field.get(target);
		if (types.isAssignableFrom(Date.class)) {
			return HDateFormatUtil.getTimeStamp((Date) obj, "yyyy-MM-HH hh:mm:ss");
		} else if (types == String.class) {
			return obj;
		}
		return obj.toString();
	}

	/***
	 * 设置值
	 * 
	 * @param target
	 * @param filedName
	 * @param value
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws NoSuchFieldException
	 * @throws SecurityException
	 */
	public static void setFiledValue(Object target, String filedName, Object value) throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException {
		Field field = target.getClass().getDeclaredField(filedName);
		field.setAccessible(true);
		field.set(target, value);
	}
}
