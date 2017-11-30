package com.happ.webcore.db.mybatis;

import java.lang.reflect.Field;
import java.util.Map;

import org.apache.ibatis.jdbc.SQL;

import com.happ.webcore.base.HBaseBean;
import com.happ.webcore.base.conf.HAPPConstance;
import com.happ.webcore.db.exception.HDBException;
import com.happ.webcore.db.mybatis.annotation.HMybatisColumn;
import com.happ.webcore.db.mybatis.annotation.HMybatisId;
import com.happ.webcore.db.mybatis.annotation.HMybatisIncremental;
import com.happ.webcore.db.mybatis.util.HMybatisReflectUtil;

public class HDBSqlProvider {

	/***
	 * 插入sql
	 * 
	 * @param entity
	 * @return
	 */
	public String insert(Object entity) {
		if (!(entity instanceof HBaseBean)) {
			throw new HDBException("自动生成insert sql异常:参数非HBaseBean");
		}
		SQL sql = new SQL();
		sql.INSERT_INTO(HMybatisReflectUtil.getTableName(entity.getClass()));
		try {
			Field fileds[] = entity.getClass().getDeclaredFields();
			for (Field filed : fileds) {
				filed.setAccessible(true);
				String name = filed.getName();
				String key = name;
				Object value = null;
				HMybatisColumn col = filed.getDeclaredAnnotation(HMybatisColumn.class);
				if (col != null) {
					key = col.value();
				}
				HMybatisIncremental increment = filed.getDeclaredAnnotation(HMybatisIncremental.class);
				if (increment != null) {
					// 自动增长
					if (increment.auto()) {
						continue;
					}
					String seq = increment.OracleSequence();
					if (seq.trim().length() == 0) {
						continue;
					}
					value = sql + ".NEXTVAL";
				} else {
					value = HMybatisReflectUtil.getFiledValue(filed, entity);
				}
				if (value != null) {
					sql.VALUES(key, value.toString());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new HDBException("自动生成insert sql异常", e);
		}
		return sql.toString();
	}

	/***
	 * 更新sql
	 * 
	 * @param entity
	 * @return
	 */
	public String update(Object entity) {
		if (!(entity instanceof HBaseBean)) {
			throw new HDBException("自动生成update sql异常:参数非HBaseBean");
		}
		SQL sql = new SQL();
		sql.UPDATE(HMybatisReflectUtil.getTableName(entity.getClass()));
		// 是否有主键
		boolean hasId = false;
		try {
			Field fileds[] = entity.getClass().getDeclaredFields();
			for (Field filed : fileds) {
				filed.setAccessible(true);
				String name = filed.getName();
				String key = name;
				Object value = null;
				HMybatisColumn col = filed.getDeclaredAnnotation(HMybatisColumn.class);
				if (col != null) {
					key = col.value();
				}
				value = HMybatisReflectUtil.getFiledValue(filed, entity);
				if (value == null) {
					continue;
				}
				HMybatisId id = filed.getDeclaredAnnotation(HMybatisId.class);
				if (id != null) {
					// 主键
					hasId = true;
					sql.WHERE(key + " = #{" + key + "}");
				} else {
					sql.SET(key + " = #{" + key + "}");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new HDBException("自动生成update sql异常", e);
		}
		if (!hasId) {
			throw new HDBException("自动生成update sql异常:缺少更新主键条件");
		}
		return sql.toString();
	}

	/***
	 * 删除sql
	 * 
	 * @param entity
	 * @return
	 */
	public String delete(Object entity) {
		if (!(entity instanceof HBaseBean)) {
			throw new HDBException("自动生成delete sql异常:参数非HBaseBean");
		}
		SQL sql = new SQL();
		sql.DELETE_FROM(HMybatisReflectUtil.getTableName(entity.getClass()));
		try {
			Field fileds[] = entity.getClass().getDeclaredFields();
			for (Field filed : fileds) {
				filed.setAccessible(true);
				String name = filed.getName();
				String key = name;
				Object value = null;
				HMybatisColumn col = filed.getDeclaredAnnotation(HMybatisColumn.class);
				if (col != null) {
					key = col.value();
				}
				value = HMybatisReflectUtil.getFiledValue(filed, entity);
				if (value == null) {
					continue;
				}
				sql.WHERE(key + " =#{" + key + "}");
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new HDBException("自动生成delete sql异常", e);
		}

		return sql.toString();
	}

	/***
	 * 插入sql
	 * 
	 * @param params
	 * @return
	 */
	public String insertMap(Map<?, ?> params) {
		if (!params.containsKey(HAPPConstance.DB_Mybatis_autoSql_BeanClass)) {
			throw new HDBException("自动生成insert sql异常:缺少bean class");
		}
		Class<?> classz = (Class<?>) params.remove(HAPPConstance.DB_Mybatis_autoSql_BeanClass);
		SQL sql = new SQL();
		sql.INSERT_INTO(HMybatisReflectUtil.getTableName(classz));
		Field fileds[] = classz.getDeclaredFields();
		for (Field filed : fileds) {
			filed.setAccessible(true);
			String name = filed.getName();
			String key = name;
			Object value = null;
			HMybatisColumn col = filed.getDeclaredAnnotation(HMybatisColumn.class);
			if (col != null) {
				key = col.value();
			}
			HMybatisIncremental increment = filed.getDeclaredAnnotation(HMybatisIncremental.class);
			if (increment != null) {
				// 自动增长
				if (increment.auto()) {
					continue;
				}
				String seq = increment.OracleSequence();
				if (seq.trim().length() == 0) {
					continue;
				}
				value = sql + ".NEXTVAL";
				sql.VALUES(key, value.toString());
				continue;
			} else {
				value = params.get(key);
				if (value == null) {
					value = params.get(name);
				}
			}
			if (value != null) {
				sql.VALUES(key, "#{" + key + "}");
			}
		}
		return sql.toString();
	}

	/***
	 * 更新sql
	 * 
	 * @param params
	 * @return
	 */
	public String updateMap(Map<?, ?> params) {
		if (!params.containsKey(HAPPConstance.DB_Mybatis_autoSql_BeanClass)) {
			throw new HDBException("自动生成update sql异常:缺少bean class");
		}
		Class<?> classz = (Class<?>) params.remove(HAPPConstance.DB_Mybatis_autoSql_BeanClass);
		SQL sql = new SQL();
		sql.UPDATE(HMybatisReflectUtil.getTableName(classz));
		// 是否有主键
		boolean hasId = false;
		try {
			Field fileds[] = classz.getDeclaredFields();
			for (Field filed : fileds) {
				filed.setAccessible(true);
				String name = filed.getName();
				String key = name;
				Object value = null;
				HMybatisColumn col = filed.getDeclaredAnnotation(HMybatisColumn.class);
				if (col != null) {
					key = col.value();
				}
				value = params.get(key);
				if (value == null) {
					continue;
				}
				HMybatisId id = filed.getDeclaredAnnotation(HMybatisId.class);
				if (id != null) {
					// 主键
					hasId = true;
					sql.WHERE(key + " = #{" + key + "}");
				} else {
					sql.SET(key + " = #{" + key + "}");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new HDBException("自动生成update sql异常", e);
		}
		if (!hasId) {
			throw new HDBException("自动生成update sql异常:缺少更新主键条件");
		}
		String sqlStr= sql.toString();
		return sqlStr;
	}

	
	/***
	 * 删除sql
	 * @param params
	 * @return
	 */
	public String deleteMap(Map<?, ?> params) {
		if (!params.containsKey(HAPPConstance.DB_Mybatis_autoSql_BeanClass)) {
			throw new HDBException("自动生成update sql异常:缺少bean class");
		}
		Class<?> classz = (Class<?>) params.remove(HAPPConstance.DB_Mybatis_autoSql_BeanClass);
		SQL sql = new SQL();
		sql.DELETE_FROM(HMybatisReflectUtil.getTableName(classz));
		try {
			Field fileds[] = classz.getDeclaredFields();
			for (Field filed : fileds) {
				filed.setAccessible(true);
				String name = filed.getName();
				String key = name;
				Object value = null;
				HMybatisColumn col = filed.getDeclaredAnnotation(HMybatisColumn.class);
				if (col != null) {
					key = col.value();
				}
				value = params.get(key);
				if (value == null) {
					continue;
				}
				sql.WHERE(key + " = #{" + key + "}");
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new HDBException("自动生成delete sql异常", e);
		}

		return sql.toString();
	}
}
