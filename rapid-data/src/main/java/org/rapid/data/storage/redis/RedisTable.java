package org.rapid.data.storage.redis;

import org.rapid.data.storage.db.Entity;
import org.rapid.data.storage.db.Table;

/**
 * redis 中的 table
 * 
 * @author ahab
 */
public class RedisTable<KEY, ENTITY extends Entity<KEY>> extends Table<KEY, ENTITY> {
	
	private int expireMillisSeconds;				// 该表的数据的有效时间

	public RedisTable(String name, Class<ENTITY> clazz) {
		super(name, clazz);
	}
	
	public RedisTable(String name, Class<ENTITY> clazz, int expireMillisSeconds) {
		super(name, clazz);
		this.expireMillisSeconds = expireMillisSeconds;
	}

	public int expireMillisSeconds() {
		return expireMillisSeconds;
	}
}
