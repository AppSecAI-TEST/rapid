package org.rapid.data.storage.mapper;

import org.rapid.util.common.model.UniqueModel;

public abstract class RawMemoryMapper<KEY, MODEL extends UniqueModel<KEY>> extends Mapper<String, KEY, MODEL> {

	public RawMemoryMapper(String redisKey) {
		this.redisKey = redisKey;
	}
	
	@Override
	public MODEL insert(MODEL model) {
		redis.hset(redisKey, model.key().toString(), serial(model));
		return model;
	}
	@Override
	public MODEL getByKey(KEY key) {
		String data = redis.hget(redisKey, key.toString());
		return null == data ? null : deserial(data);
	}
	@Override
	public void update(MODEL model) {
		redis.hset(redisKey, model.key().toString(), serial(model));		
	}
}
