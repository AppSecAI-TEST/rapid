package org.rapid.redis.service;

import java.util.List;

import org.rapid.redis.Redis;
import org.rapid.util.common.SerializeUtil;
import org.rapid.util.common.db.Entity;
import org.rapid.util.common.db.EntitySerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class RedisService<KEY, ENTITY extends Entity<KEY>> implements IRedisService<KEY, ENTITY> {
	
	private static final Logger logger =  LoggerFactory.getLogger(RedisService.class);

	private Redis redis;
	private byte[] redisKey;
	private Class<ENTITY> clazz;									// ENTITY 的 class 类，用在反序列化中
	private EntitySerializer<KEY, ENTITY, byte[]> serializer;		// 序列化类
	
	protected RedisService(Class<ENTITY> clazz,  String redisKey) {
		this.clazz = clazz;
		this.redisKey = SerializeUtil.RedisUtil.encode(redisKey);
	}
	
	@Override
	public void load() {
		List<ENTITY> list = loadFromDB();
		_store(list);
		logger.info("Table {} successfully cached, total {} records were loaded!", clazz.getSimpleName(), list.size());
	}
	
	/**
	 * 从 DB 中获取数据
	 * 
	 * @return
	 */
	protected abstract List<ENTITY> loadFromDB();  
	
	/**
	 * 缓存数据库数据
	 * 
	 * @param identity 序列化类型
	 * @param table 需要序列表的表
	 * @param entities 序列表实体列表
	 */
	private void _store(List<ENTITY> entities) {
		int len = entities.size();
		byte[][] data = new byte[len * 2 + 1][];
		data[0] = redisKey;
		for (int i = 0; i < len; i++) {
			ENTITY entity = entities.get(i);
			data[i * 2 + 1] = SerializeUtil.RedisUtil.encode(entity.key().toString());
			data[i * 2 + 2] = serializer.convert(entity);
		}
		redis.delAndHmset(data);
	}
	
	@Override
	public ENTITY getById(KEY key) {
		byte[] data = redis.hget(redisKey, SerializeUtil.RedisUtil.encode(key.toString()));
		return serializer.antiConvet(data, clazz);
	}
	
	public void setRedis(Redis redis) {
		this.redis = redis;
	}
	
	public void setSerializer(EntitySerializer<KEY, ENTITY, byte[]> serializer) {
		this.serializer = serializer;
	}
}
