package org.rapid.data.storage.mapper;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import org.rapid.data.storage.redis.Redis;
import org.rapid.util.common.model.UniqueModel;
import org.rapid.util.common.serializer.SerializeUtil;

public abstract class MemoryMapper<DATA, KEY, MODEL extends UniqueModel<KEY>> implements IMapper<KEY, MODEL> {
	
	protected Redis redis;
	protected String redisKey;
	protected Class<MODEL> clazz;
	protected Class<?> dataClazz;
	
	/**
	 * @param redisKey 存放 hash 的 key
	 * @param raw 是否是原生的 redis 字符串，如果不是则为字节数组
	 */
	@SuppressWarnings("unchecked")
	public MemoryMapper(String redisKey) {
		this.redisKey = redisKey;
		Type superType = getClass().getGenericSuperclass();   
		Type[] generics = ((ParameterizedType) superType).getActualTypeArguments();  
		dataClazz = (Class<?>) generics[0];
		clazz = (Class<MODEL>) generics[2];
	}
	
	@Override
	public MODEL insert(MODEL model) {
		if (dataClazz == String.class)
			redis.hset(redisKey, model.key().toString(), (String) serial(model));
		else 
			redis.hset(SerializeUtil.RedisUtil.encode(redisKey), 
					SerializeUtil.RedisUtil.encode(model.key().toString()), 
					(byte[]) serial(model));
		return model;
	}

	@Override
	@SuppressWarnings("unchecked")
	public MODEL getByKey(KEY key) {
		if (dataClazz == String.class) {
			String data = redis.hget(redisKey, key.toString());
			return null == data ? null : deserial((DATA) data);
		} else {
			byte[] data = redis.hget(SerializeUtil.RedisUtil.encode(redisKey), 
					SerializeUtil.RedisUtil.encode(key.toString()));
			return null == data ? null : deserial((DATA)data);
		}
	}

	@Override
	public void update(MODEL model) {
		if (dataClazz == String.class) 
			redis.hset(redisKey, model.key().toString(), (String) serial(model));
		else
			redis.hset(SerializeUtil.RedisUtil.encode(redisKey), 
					SerializeUtil.RedisUtil.encode(model.key().toString()), 
					(byte[]) serial(model));
	}
	
	protected abstract DATA serial(MODEL model);
	
	protected abstract MODEL deserial(DATA data);
	
	public void setRedis(Redis redis) {
		this.redis = redis;
	}
}
