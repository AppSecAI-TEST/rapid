package org.rapid.data.storage.mapper;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import javax.annotation.Resource;

import org.rapid.data.storage.redis.Redis;
import org.rapid.util.common.model.UniqueModel;
import org.rapid.util.common.serializer.SerializeUtil;

public abstract class MemoryMapper<KEY, MODEL extends UniqueModel<KEY>> implements IMapper<KEY, MODEL> {
	
	@Resource
	protected Redis redis;
	protected byte[] redisKey;
	protected Class<MODEL> clazz;
	
	@SuppressWarnings("unchecked")
	public MemoryMapper(String redisKey) {
		this.redisKey = SerializeUtil.RedisUtil.encode(redisKey);
		this.redisKey = SerializeUtil.RedisUtil.encode(redisKey);
		Type superType = getClass().getGenericSuperclass();   
		Type[] generics = ((ParameterizedType) superType).getActualTypeArguments();   
		clazz = (Class<MODEL>) generics[1];
	}

	@Override
	public MODEL insert(MODEL model) {
		redis.hset(redisKey, SerializeUtil.RedisUtil.encode(model.key().toString()), serial(model));
		return model;
	}

	@Override
	public MODEL getByKey(KEY key) {
		byte[] data = redis.hget(redisKey, SerializeUtil.RedisUtil.encode(key.toString()));
		return null == data ? null : deserial(data);
	}

	@Override
	public void update(MODEL model) {
		redis.hset(redisKey, SerializeUtil.RedisUtil.encode(model.key().toString()), serial(model));
	}
	
	protected abstract byte[] serial(MODEL model);
	
	protected abstract MODEL deserial(byte[] data);
	
	public void setRedis(Redis redis) {
		this.redis = redis;
	}
}
