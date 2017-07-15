package org.rapid.data.storage.mybatis;

import java.util.Collection;
import java.util.Map;

import org.apache.ibatis.jdbc.SQL;

public class SQLProvider {
	
	private static final String COLLECTION					= "collection";
	
	protected final String table;
	protected final String keyCol;
	
	protected SQLProvider(String table, String keyCol) {
		this.table = table;
		this.keyCol = keyCol;
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
	
	public String delete() {
		return new SQL() {
			{
				DELETE_FROM(table);
				WHERE(keyCol + "=#{key}");
			}
		}.toString();
	}
}
