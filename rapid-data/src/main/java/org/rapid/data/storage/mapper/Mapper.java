package org.rapid.data.storage.mapper;

import org.rapid.data.storage.db.Dao;
import org.rapid.data.storage.db.Entity;
import org.rapid.data.storage.redis.Redis;
import org.rapid.data.storage.redis.RedisTable;

/**
 * Redis 数据库映射器，将数据库表映射存储到 redis 中
 * 
 * @author ahab
 *
 * @param <KEY>
 * @param <ENTITY>
 */
public abstract class Mapper<KEY, ENTITY extends Entity<KEY>, DAO extends Dao<KEY, ENTITY>> implements IMapper<KEY, ENTITY> {
	
	protected DAO dao;
	protected Redis redis;
	protected RedisTable<KEY, ENTITY> table;
	
	protected Mapper(RedisTable<KEY, ENTITY> table) {
		this.table = table;
	}
	
	public void setRedis(Redis redis) {
		this.redis = redis;
	}
	
	public void setDao(DAO dao) {
		this.dao = dao;
	}
}
