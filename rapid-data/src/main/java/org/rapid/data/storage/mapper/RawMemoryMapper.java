package org.rapid.data.storage.mapper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
	@SuppressWarnings("unchecked")
	public List<MODEL> getWithinKey(List<KEY> keys) {
		if (null == keys || keys.isEmpty())
			return Collections.EMPTY_LIST;
		String[] fields = new String[keys.size()];
		int index = 0;
		for (KEY key : keys)
			fields[index++] = key.toString();
		List<String> datas = redis.hmget(redisKey, fields);
		List<MODEL> models = new ArrayList<MODEL>();
		for (String data : datas) 
			models.add(deserial(data));
		return models;
	}
	
	@Override
	public void update(MODEL model) {
		redis.hset(redisKey, model.key().toString(), serial(model));		
	}
}
