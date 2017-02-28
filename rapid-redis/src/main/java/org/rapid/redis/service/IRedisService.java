package org.rapid.redis.service;

import org.rapid.util.common.db.Entity;

/**
 * 每一张表对应一个 RedisService 类提供该表数据的缓存存取策略
 * 
 * @author ahab
 *
 * @param <T> 表对应的实体类
 */
public interface IRedisService<KEY, ENTITY extends Entity<KEY>> {

	/**
	 * 加载数据到缓存
	 */
	void load();
	
	/**
	 * 只能根据主键来获取指定的对象
	 */
	ENTITY getById(KEY key);
}
