package org.rapid.data.storage.db;

/**
 * 表示数据库的表
 * 
 * @author ahab
 *
 * @param <KEY> 表的主键类型
 * @param <ENTITY> 表的实体映射类型
 */
public class Table {

	protected String name;
	
	public Table(String name) {
		this.name = name;
	}

	public String name() {
		return name;
	}
}
