package org.rapid.data.storage.mapper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.rapid.util.common.model.UniqueModel;
import org.rapid.util.common.serializer.SerializeUtil;

public abstract class RedisBinaryMemoryMapper<KEY, MODEL extends UniqueModel<KEY>> extends RedisMapper<byte[], KEY, MODEL> {
	
	public RedisBinaryMemoryMapper(String redisKey) {
		this.redisKey = SerializeUtil.RedisUtil.encode(redisKey);
	}
	
	@Override
	public MODEL insert(MODEL model) {
		redis.hset(redisKey, SerializeUtil.RedisUtil.encode(model.key()), serial(model));
		return model;
	}

	@Override
	public MODEL getByKey(KEY key) {
		byte[] data = redis.hget(redisKey, key);
		return null == data ? null : deserial(data);
	}
	
	@Override
	public List<MODEL> getWithinKey(List<KEY> keys) {
		if (null == keys || keys.isEmpty())
			return Collections.EMPTY_LIST;
		Object[] fields = new Object[keys.size()];
		int index = 0;
		for (KEY key : keys)
			fields[index++] = key;
		List<byte[]> datas = redis.hmget(redisKey, fields);
		List<MODEL> models = new ArrayList<MODEL>();
		for (byte[] data : datas) 
			models.add(deserial(data));
		return models;
	}

	@Override
	public void update(MODEL model) {
		redis.hset(redisKey, SerializeUtil.RedisUtil.encode(model.key()), serial(model));
	}
}
