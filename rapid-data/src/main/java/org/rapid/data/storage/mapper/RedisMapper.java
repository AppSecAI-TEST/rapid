package org.rapid.data.storage.mapper;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import javax.annotation.Resource;

import org.rapid.data.storage.redis.Redis;
import org.rapid.util.common.model.UniqueModel;

public abstract class RedisMapper<DATA, KEY, MODEL extends UniqueModel<KEY>> implements IMapper<KEY, MODEL> {

	@Resource
	protected Redis redis;
	protected DATA redisKey;
	protected Class<MODEL> clazz;
	
	public RedisMapper() {
		Type superType = getClass().getGenericSuperclass();   
		Type[] generics = ((ParameterizedType) superType).getActualTypeArguments();  
		clazz = (Class<MODEL>) generics[1];
	}
	
	protected abstract DATA serial(MODEL model);
	
	protected abstract MODEL deserial(DATA data);
	
	public void setRedis(Redis redis) {
		this.redis = redis;
	}
}
