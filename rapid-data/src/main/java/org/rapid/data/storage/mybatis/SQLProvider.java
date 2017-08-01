package org.rapid.data.storage.mybatis;

import java.util.Collection;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.ibatis.jdbc.SQL;
import org.rapid.util.lang.StringUtil;
import org.rapid.util.reflect.BeanUtils;

public class SQLProvider {
	
	protected static final String COLLECTION					= "collection";
	
	protected String table;
	protected String keyCol;
	protected boolean useGeneratedKeys;						// 是否自动生成主键默认自动生成主键
	
	protected SQLProvider(String table, String keyCol) {
		this(table, keyCol, true);
	}
	
	protected SQLProvider(String table, String keyCol, boolean useGeneratedKeys) {
		this.table = table;
		this.keyCol = keyCol;
		this.useGeneratedKeys = useGeneratedKeys;
	}
	
	public String insert(Object entity) {
		Map<String, Object> params = BeanUtils.beanToMap(entity, false);
		SQL sql = new SQL();
		sql.INSERT_INTO(table);
		for (Entry<String, Object> entry : params.entrySet()) {
			String col = StringUtil.camel2Underline(entry.getKey());
			if (useGeneratedKeys && keyCol.equals(col))					// 如果主键是自动生成的则要忽略主键
				continue;
			sql.VALUES("`" + col + "`", "#{" + entry.getKey() + "}");
		}
		return sql.toString();
	}
	
	public String getAll() {
		return new SQL() {
			{
				SELECT("*");
				FROM(table);
			}
		}.toString();
	}
	
	public String getByKey() {
		return new SQL() {
			{
				SELECT("*");
				FROM(table);
				WHERE(keyCol + "=#{key}");
			}
		}.toString();
	}
	
	public String getByKeys(Map<String, Object> params) {
		Collection<?> keys = (Collection<?>) params.get(COLLECTION);
		StringBuilder sql = new StringBuilder("SELECT * FROM ");
		sql.append(table).append("  WHERE ").append(keyCol).append(" IN(");
		for (Object key : keys)
			sql.append(key).append(",");
		sql.deleteCharAt(sql.length() - 1);
		sql.append(")");
		return sql.toString();
	}
	
	public String update(Object entity) {
		Map<String, Object> params = BeanUtils.beanToMap(entity, true);			// null 值也要包括进来，全属性更新
		SQL sql = new SQL();
		sql.UPDATE(table);
		String keyCamelCol = null;
		for (Entry<String, Object> entry : params.entrySet()) {
			String col = StringUtil.camel2Underline(entry.getKey());
			if (keyCol.equals(col))	{									// 主键不在更新之列
				keyCamelCol = entry.getKey();
				continue;
			}
			sql.SET("`" + col + "`=#{" + entry.getKey() + "}");
		}
		sql.WHERE("`" + keyCol + "`=#{" + keyCamelCol + "}");
		return sql.toString();
	}
	
	public String delete() {
		return new SQL() {
			{
				DELETE_FROM(table);
				WHERE("`" + keyCol + "`=#{key}");
			}
		}.toString();
	}
}
