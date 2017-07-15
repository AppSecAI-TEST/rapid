package org.rapid.data.storage.mapper;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.rapid.data.storage.redis.Redis;
import org.rapid.util.common.model.UniqueModel;
import org.rapid.util.common.serializer.SerializeUtil;
import org.rapid.util.common.serializer.Serializer;
import org.rapid.util.lang.CollectionUtil;

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
	public Map<KEY, MODEL> getAll() {
		Map<KEY, MODEL> map = new HashMap<KEY, MODEL>();
		List<byte[]> list = redis.hvals(redisKey);
		for (byte[] buffer : list) {
			MODEL model = serializer.antiConvet(buffer);
			map.put(model.key(), model);
		}
		return map;
	}

	@Override
	public MODEL getByKey(KEY key) {
		byte[] data = redis.hget(redisKey, key);
		return null == data ? null : serializer.antiConvet(data);
	}

	@Override
	public Map<KEY, MODEL> getByKeys(Collection<KEY> keys) {
		Map<KEY, MODEL> map = new HashMap<KEY, MODEL>();
		if (!CollectionUtil.isEmpty(keys)) {
			List<byte[]> datas = redis.hmget(redisKey, keys);
			for (byte[] data : datas) {
				if (null == data)
					continue;
				MODEL model = serializer.antiConvet(data);
				map.put(model.key(), model);
			}
		}
		return map;
	}
	
	@Override
	public void update(MODEL model) {
		this.flush(model);
	}
	
	@Override
	public void delete(KEY key) {
		MODEL model = getByKey(key);
		if (null == model)
			return;
		this.remove(model);
	}
	
	public void flush(MODEL model) {
		redis.hset(redisKey, model.key(), serializer.convert(model));
	}
	
	public void remove(MODEL model) {
		redis.hdel(redisKey, model.key());
	}
	
	public void flush(Map<KEY, MODEL> models) {
		if (CollectionUtil.isEmpty(models))
			return;
		Map<byte[], byte[]> map = new HashMap<byte[], byte[]>(models.size());
		for (MODEL model : models.values())
			map.put(SerializeUtil.RedisUtil.encode(model.key()), serializer.convert(model));
		redis.hmset(redisKey, map);
	}
	
	@Deprecated
	public void flush(Collection<MODEL> models) {
		if (CollectionUtil.isEmpty(models))
			return;
		Map<byte[], byte[]> map = new HashMap<byte[], byte[]>();
		for (MODEL model : models)
			map.put(SerializeUtil.RedisUtil.encode(model.key()), serializer.convert(model));
		redis.hmset(redisKey, map);
	}
	
	public void setSerializer(Serializer<MODEL, byte[]> serializer) {
		this.serializer = serializer;
	}
	
	public void setRedis(Redis redis) {
		this.redis = redis;
	}
}
