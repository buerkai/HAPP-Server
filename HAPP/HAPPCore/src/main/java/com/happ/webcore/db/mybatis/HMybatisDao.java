package com.happ.webcore.db.mybatis;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionManager;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.happ.webcore.base.HBaseBean;
import com.happ.webcore.base.conf.HAPPConstance;
import com.happ.webcore.db.exception.HDBException;
import com.happ.webcore.db.mybatis.mapper.HDBMapper;
import com.happ.webcore.db.mybatis.page.HPageInfo;

public class HMybatisDao {

	@Autowired
	@Qualifier("sqlSession")
	protected SqlSession sqlSession;

	@Autowired
	@Qualifier("sqlSessionBatch")
	protected SqlSession batchSqlSession;

	/***
	 * 分页查询数据
	 * 
	 * @param mapper
	 * @param params
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 * @throws HDBException
	 */
	public <T> List<T> selectList(String mapper, Map<String, Object> params, Integer pageIndex, Integer pageSize)
			throws HDBException {
		try {
			Page<T> page = PageHelper.startPage(pageIndex, pageSize);
			List<T> list = sqlSession.selectList(mapper, params);
			PageInfo<T> info = new PageInfo<T>(page);
			if (params != null) {
				params.put(HAPPConstance.PAGE_TOTAL, Long.valueOf(info.getTotal()));
			}
			return list;
		} catch (Exception e) {
			throw new HDBException("查询数据失败", e);
		}
	}

	/***
	 * 查询数据
	 * 
	 * @param mapper
	 * @param params
	 * @return
	 */
	public <T> T selectListOne(String mapper, Map<String, Object> params) throws HDBException {
		try {
			Page<T> page = PageHelper.startPage(1, 1);
			List<T> list = sqlSession.selectList(mapper, params);
			PageInfo<T> info = new PageInfo<T>(page);
			if (params != null) {
				params.put(HAPPConstance.PAGE_TOTAL, Long.valueOf(info.getTotal()));
			}
			if (list == null || list.isEmpty()) {
				return null;
			}
			return list.get(0);
		} catch (Exception e) {
			throw new HDBException("查询数据失败", e);
		}
	}

	/***
	 * 添加数据
	 * 
	 * @param mapper
	 * @param params
	 * @return
	 * @throws HDBException
	 */
	public long insert(String mapper, Map<String, Object> params) throws HDBException {
		try {
			return sqlSession.insert(mapper, params);
		} catch (Exception e) {
			throw new HDBException("添加数据失败", e);
		}
	}

	/***
	 * 添加数据
	 * 
	 * @param mapper
	 * @param params
	 * @return
	 * @throws HDBException
	 */
	public long insert(String mapper, JSONObject params) throws HDBException {
		try {
			return sqlSession.insert(mapper, params.toMap());
		} catch (Exception e) {
			throw new HDBException("添加数据失败", e);
		}
	}

	/***
	 * 批量添加数据
	 * 
	 * @param mapper
	 * @param params
	 * @throws HDBException
	 */
	public void insertBatch(String mapper, List<Map<String, Object>> params) throws HDBException {
		try {
			for (Map<String, Object> item : params) {
				batchSqlSession.insert(mapper, item);
			}
		} catch (Exception e) {
			throw new HDBException("批量添加数据失败", e);
		}
	}

	/***
	 * 批量添加数据
	 * 
	 * @param mapper
	 * @param params
	 * @throws HDBException
	 */
	public void insertBatch(String mapper, JSONArray params) throws HDBException {
		try {
			int count = params.length();
			for (int i = 0; i < count; i++) {
				JSONObject item = params.optJSONObject(i);
				if (item == null) {
					continue;
				}
				batchSqlSession.insert(mapper, item.toMap());
			}
		} catch (Exception e) {
			throw new HDBException("批量添加数据失败", e);
		}
	}

	/***
	 * 更新数据
	 * 
	 * @param mapper
	 * @param params
	 * @return
	 * @throws HDBException
	 */
	public long update(String mapper, Map<String, Object> params) throws HDBException {
		try {
			return sqlSession.update(mapper, params);
		} catch (Exception e) {
			throw new HDBException("更新数据失败", e);
		}
	}

	/***
	 * 更新数据
	 * 
	 * @param mapper
	 * @param params
	 * @return
	 * @throws HDBException
	 */
	public long update(String mapper, JSONObject params) throws HDBException {
		try {
			return sqlSession.update(mapper, params.toMap());
		} catch (Exception e) {
			throw new HDBException("更新数据失败", e);
		}
	}

	/***
	 * 更新数据
	 * 
	 * @param mapper
	 * @param updateParams
	 * @param whereParams
	 * @return
	 * @throws HDBException
	 */
	public long update(String mapper, Map<String, Object> updateParams, Map<String, Object> whereParams)
			throws HDBException {
		try {
			if (whereParams != null && !whereParams.isEmpty()) {
				Set<String> keys = whereParams.keySet();
				for (String key : keys) {
					updateParams.put("_where_" + key, whereParams.get(key));
				}
			}
			return sqlSession.update(mapper, updateParams);
		} catch (Exception e) {
			throw new HDBException("更新数据失败", e);
		}
	}

	/***
	 * 更新数据
	 * 
	 * @param mapper
	 * @param updateParams
	 * @param whereParams
	 * @return
	 * @throws HDBException
	 */
	public long update(String mapper, JSONObject updateParams, JSONObject whereParams) throws HDBException {
		try {
			if (whereParams != null && whereParams.length() > 0) {
				Set<String> keys = whereParams.keySet();
				for (String key : keys) {
					updateParams.put("_where_" + key, whereParams.get(key));
				}
			}
			return sqlSession.update(mapper, updateParams.toMap());
		} catch (Exception e) {
			throw new HDBException("更新数据失败", e);
		}
	}

	/***
	 * 批量更新数据
	 * 
	 * @param mapper
	 * @param params
	 * @return
	 * @throws HDBException
	 */
	public void updateBatch(String mapper, List<Map<String, Object>> params) throws HDBException {
		try {
			for (Map<String, Object> item : params) {
				batchSqlSession.update(mapper, item);
			}
		} catch (Exception e) {
			throw new HDBException("批量更新数据失败", e);
		}
	}

	/***
	 * 批量更新数据
	 * 
	 * @param mapper
	 * @param params
	 * @return
	 * @throws HDBException
	 */
	public void updateBatch(String mapper, JSONArray params) throws HDBException {
		try {
			int count = params.length();
			for (int i = 0; i < count; i++) {
				JSONObject item = params.optJSONObject(i);
				if (item == null) {
					continue;
				}
				batchSqlSession.update(mapper, item.toMap());
			}
		} catch (Exception e) {
			throw new HDBException("批量更新数据失败", e);
		}
	}

	/***
	 * 批量更新
	 * 
	 * @param mapper
	 * @param updateParams
	 * @param whereParams
	 * @return
	 * @throws HDBException
	 */
	public void updateBatch(String mapper, JSONArray updateParams, JSONArray whereParams) throws HDBException {
		if (updateParams.length() != whereParams.length()) {
			throw new HDBException("批量更新数据失败:输入参数错误");
		}
		try {
			int count = updateParams.length();
			for (int i = 0; i < count; i++) {
				JSONObject item = updateParams.optJSONObject(i);
				JSONObject item2 = whereParams.optJSONObject(i);
				if (item2.length() > 0) {
					Set<String> keys = item2.keySet();
					for (String key : keys) {
						item.put("_where_" + key, item2.get(key));
					}
					batchSqlSession.update(mapper, item.toMap());
				}
			}
		} catch (Exception e) {
			throw new HDBException("批量更新数据失败", e);
		}
	}

	/***
	 * 批量更新数据
	 * 
	 * @param mapper
	 * @param updateParams
	 * @param whereParams
	 * @return
	 * @throws HDBException
	 */
	public void updateBatch(String mapper, List<Map<String, Object>> updateParams,
			List<Map<String, Object>> whereParams) throws HDBException {
		if (updateParams.size() != whereParams.size()) {
			throw new HDBException("批量更新数据失败:输入参数错误");
		}
		try {
			int count = updateParams.size();
			for (int i = 0; i < count; i++) {
				Map<String, Object> item = updateParams.get(i);
				Map<String, Object> item2 = whereParams.get(i);
				if (!item2.isEmpty()) {
					Set<String> keys = item2.keySet();
					for (String key : keys) {
						item.put("_where_" + key, item2.get(key));
					}
				}
				batchSqlSession.update(mapper, item);
			}
		} catch (Exception e) {
			throw new HDBException("批量更新数据失败", e);
		}
	}

	/***
	 * 删除数据
	 * 
	 * @param mapper
	 * @param params
	 * @return
	 */
	public long delete(String mapper, Map<String, Object> params) {
		try {
			return sqlSession.delete(mapper, params);
		} catch (Exception e) {
			throw new HDBException("删除数据失败", e);
		}
	}

	/***
	 * 批量删除数据
	 * 
	 * @param mapper
	 * @param params
	 * @return
	 */
	public void deleteBatch(String mapper, JSONArray params) {
		try {
			int count = params.length();
			for (int i = 0; i < count; i++) {
				JSONObject item = params.optJSONObject(i);
				if (item == null) {
					continue;
				}
				batchSqlSession.delete(mapper, item.toMap());
			}
		} catch (Exception e) {
			throw new HDBException("批量批量删除数据失败", e);
		}
	}

	/***
	 * 批量删除数据
	 * 
	 * @param mapper
	 * @param params
	 * @return
	 */
	public void deleteBatch(String mapper, List<Map<String, Object>> params) {
		try {
			int count = params.size();
			for (int i = 0; i < count; i++) {
				batchSqlSession.delete(mapper, params.get(i));
			}
		} catch (Exception e) {
			throw new HDBException("批量删除数据失败", e);
		}
	}

	/***
	 * 删除数据
	 * 
	 * @param mapper
	 * @param params
	 * @return
	 */
	public long delete(String mapper, JSONObject params) {
		try {
			return sqlSession.delete(mapper, params.toMap());
		} catch (Exception e) {
			throw new HDBException("删除数据失败", e);
		}
	}

	public Map<Object, Object> selectOne_DBMapper(String mapper, Map<?, ?> params) throws HDBException {
		try {
			HPageInfo pageInfo = new HPageInfo(1, 1);
			List<Object> list = sqlSession.selectList(mapper, params, pageInfo);
			if (list == null || list.isEmpty()) {
				return null;
			}
			return (Map<Object, Object>) list.get(0);
		} catch (Exception e) {
			throw new HDBException(e);
		}
	}

	public Map<Object, Object> selectOne_DBMapper(String mapper) throws HDBException {
		try {
			HPageInfo pageInfo = new HPageInfo(1, 1);
			List<Object> list = sqlSession.selectList(mapper, null, pageInfo);
			if (list == null || list.isEmpty()) {
				return null;
			}
			return (Map<Object, Object>) list.get(0);
		} catch (Exception e) {
			throw new HDBException(e);
		}
	}

	public List<Object> select_DBMapper(String mapper, Map<?, ?> params, Long pageIndex, Long pageSize)
			throws HDBException {
		try {
			HPageInfo pageInfo = new HPageInfo(pageIndex, pageSize);
			List<Object> ret = sqlSession.selectList(mapper, params, pageInfo);
			return ret;
		} catch (Exception e) {
			throw new HDBException(e);
		}
	}

	public List<Object> select_DBMapper(String mapper, long pageIndex, long pageSize) throws HDBException {
		try {
			return sqlSession.selectList(mapper);
		} catch (Exception e) {
			throw new HDBException(e);
		}
	}

	public long insert_DBMapper(String mapper, Map<Object, Object> params) throws HDBException {
		return sqlSession.insert(mapper, params);
	}

	/***
	 * 插入数据
	 * 
	 * @param beanClassz
	 * @param params
	 * @return
	 * @throws HDBException
	 */
	public long insert_DBMapper(Class<? extends HBaseBean> beanClassz, Map<Object, Object> params) throws HDBException {
		try {
			params.put(HAPPConstance.DB_Mybatis_autoSql_BeanClass, beanClassz);
			return sqlSession.getMapper(HDBMapper.class).insertMap(params);
		} catch (Exception e) {
			throw new HDBException(e);
		}
	}

	/**
	 * 插入map
	 * 
	 * @param classz
	 *            bean beanClassz
	 * @param params
	 *            参数
	 * @return
	 * @throws HDBException
	 */
	public long insert_DBMapper(Class<? extends HBaseBean> beanClassz, JSONObject params) throws HDBException {
		try {
			params.put(HAPPConstance.DB_Mybatis_autoSql_BeanClass, beanClassz);
			return sqlSession.getMapper(HDBMapper.class).insertMap(params.toMap());
		} catch (Exception e) {
			throw new HDBException(e);
		} finally {
			params.remove(HAPPConstance.DB_Mybatis_autoSql_BeanClass);
		}
	}

	/***
	 * 插入bean
	 * 
	 * @param bean
	 * @return
	 * @throws HDBException
	 */
	public long insert_DBMapper(Object bean) throws HDBException {
		try {
			return sqlSession.getMapper(HDBMapper.class).insert(bean);
		} catch (Exception e) {
			throw new HDBException(e);
		}
	}

	/***
	 * 更新bean
	 * 
	 * @param bean
	 * @return
	 * @throws HDBException
	 */
	public int update_DBMapper(Object bean) throws HDBException {
		try {
			return sqlSession.getMapper(HDBMapper.class).update(bean);
		} catch (Exception e) {
			throw new HDBException(e);
		}
	}

	/***
	 * 更新数据
	 * 
	 * @param beanClassz
	 * @param params
	 * @return
	 */
	public int update_DBMapper(Class<? extends HBaseBean> beanClassz, Map<Object, Object> params) throws HDBException {
		try {
			params.put(HAPPConstance.DB_Mybatis_autoSql_BeanClass, beanClassz);
			return sqlSession.getMapper(HDBMapper.class).updateMap(params);
		} catch (Exception e) {
			throw new HDBException(e);
		}
	}

	/***
	 * 更新数据
	 * 
	 * @param beanClassz
	 * @param params
	 * @return
	 */
	public int update_DBMapper(Class<? extends HBaseBean> beanClassz, JSONObject params) throws HDBException {
		try {
			params.put(HAPPConstance.DB_Mybatis_autoSql_BeanClass, beanClassz);
			return sqlSession.getMapper(HDBMapper.class).updateMap(params.toMap());
		} catch (Exception e) {
			throw new HDBException(e);
		} finally {
			params.remove(HAPPConstance.DB_Mybatis_autoSql_BeanClass);
		}
	}

	/***
	 * 删除数据
	 * 
	 * @param bean
	 * @return
	 * @throws HDBException
	 */
	public int delete_DBMapper(Object bean) throws HDBException {
		try {
			return sqlSession.getMapper(HDBMapper.class).delete(bean);
		} catch (Exception e) {
			throw new HDBException(e);
		}
	}

	/***
	 * 删除数据
	 * 
	 * @param beanClassz
	 * @param params
	 * @return
	 * @throws HDBException
	 */
	public int delete_DBMapper(Class<? extends HBaseBean> beanClassz, Map<Object, Object> params) throws HDBException {
		try {
			params.put(HAPPConstance.DB_Mybatis_autoSql_BeanClass, beanClassz);
			return sqlSession.getMapper(HDBMapper.class).deleteMap(params);
		} catch (Exception e) {
			throw new HDBException(e);
		}
	}

	/***
	 * 删除数据
	 * 
	 * @param beanClassz
	 * @param params
	 * @return
	 * @throws HDBException
	 */
	public int delete_DBMapper(Class<? extends HBaseBean> beanClassz, JSONObject params) throws HDBException {
		try {
			params.put(HAPPConstance.DB_Mybatis_autoSql_BeanClass, beanClassz);
			return sqlSession.getMapper(HDBMapper.class).deleteMap(params.toMap());
		} catch (Exception e) {
			throw new HDBException(e);
		} finally {
			params.remove(HAPPConstance.DB_Mybatis_autoSql_BeanClass);
			SqlSessionManager gg;
		}

	}
}
