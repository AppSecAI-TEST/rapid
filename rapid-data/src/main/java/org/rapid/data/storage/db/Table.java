package org.rapid.data.storage.db;

/**
 * 表示数据库的表
 * 
 * @author ahab
 *
 * @param <KEY> 表的主键类型
 * @param <ENTITY> 表的实体映射类型
 */
public class Table<KEY, ENTITY extends Entity<KEY>> {

	protected String name;
	protected Class<ENTITY> clazz;			// 表对应的实体类型的 class
	
	public Table(String name, Class<ENTITY> clazz) {
		this.name = name;
		this.clazz = clazz;
	}

	public String name() {
		return name;
	}
	
	public Class<ENTITY> entityClass() {
		return clazz;
	}
}
