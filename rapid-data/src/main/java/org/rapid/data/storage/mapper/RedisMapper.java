package org.rapid.data.storage.mapper;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.rapid.data.storage.redis.Redis;
import org.rapid.util.common.model.UniqueModel;
import org.rapid.util.common.serializer.SerializeUtil;
import org.rapid.util.common.serializer.Serializer;

/**
 * 将普通的 java 对象映射到 redis 中
 * 
 * @author ahab
 *
 * @param <KEY>
 * @param <MODEL>
 */
public class RedisMapper<KEY, MODEL extends UniqueModel<KEY>> implements Mapper<KEY, MODEL> {
	
	@Resource
	protected Redis redis;
	protected byte[] redisKey;
	protected Class<MODEL> clazz;
	protected Serializer<MODEL, byte[]> serializer;
	
	protected RedisMapper(Serializer<MODEL, byte[]> serializer, String redisKey) {
		Type superType = getClass().getGenericSuperclass();   
		Type[] generics = ((ParameterizedType) superType).getActualTypeArguments();  
		this.clazz = (Class<MODEL>) generics[1];
		this.serializer = serializer;
		this.serializer.setClazz(clazz);
		this.redisKey = SerializeUtil.RedisUtil.encode(redisKey);
	}

	@Override
	public void insert(MODEL model) {
		this.flush(model);
	}

	@Override
	public MODEL getByKey(KEY key) {
		byte[] data = redis.hget(redisKey, key);
		return null == data ? null : serializer.antiConvet(data);
	}

	@Override
	public List<MODEL> getWithinKey(List<KEY> keys) {
		if (null == keys || keys.isEmpty())
			return Collections.EMPTY_LIST;
		List<byte[]> datas = redis.hmget(redisKey, keys);
		List<MODEL> models = new ArrayList<MODEL>();
		for (byte[] data : datas) 
			models.add(serializer.antiConvet(data));
		return models;
	}

	@Override
	public void update(MODEL model) {
		this.flush(model);
	}
	
	@Override
	public void delete(KEY key) {
		
	}
	
	public void flush(MODEL model) {
		redis.hset(redisKey, model.key(), serializer.convert(model));
	}
	
	public void flush(Collection<MODEL> models) {
		Map<byte[], byte[]> map = new HashMap<byte[], byte[]>();
		for (MODEL model : models)
			map.put(SerializeUtil.RedisUtil.encode(model.key()), serializer.convert(model));
		redis.hmset(redisKey, map);
	}
	
	public void setRedis(Redis redis) {
		this.redis = redis;
	}
	
	public void setSerializer(Serializer<MODEL, byte[]> serializer) {
		this.serializer = serializer;
	}
}
