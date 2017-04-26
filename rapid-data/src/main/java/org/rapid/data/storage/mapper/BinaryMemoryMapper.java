package org.rapid.data.storage.mapper;

import org.rapid.util.common.model.UniqueModel;
import org.rapid.util.common.serializer.SerializeUtil;

public abstract class BinaryMemoryMapper<KEY, MODEL extends UniqueModel<KEY>> extends Mapper<byte[], KEY, MODEL> {
	
	public BinaryMemoryMapper(String redisKey) {
		this.redisKey = SerializeUtil.RedisUtil.encode(redisKey);
	}
	
	@Override
	public MODEL insert(MODEL model) {
		redis.hset(redisKey, SerializeUtil.RedisUtil.encode(model.key()), serial(model));
		return model;
	}

	@Override
	public MODEL getByKey(KEY key) {
		byte[] data = redis.hget(redisKey, SerializeUtil.RedisUtil.encode(key));
		return null == data ? null : deserial(data);
	}

	@Override
	public void update(MODEL model) {
		redis.hset(redisKey, SerializeUtil.RedisUtil.encode(model.key()), serial(model));
	}
}
