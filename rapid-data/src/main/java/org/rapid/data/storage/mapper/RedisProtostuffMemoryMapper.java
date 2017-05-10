package org.rapid.data.storage.mapper;

import org.rapid.util.common.model.UniqueModel;
import org.rapid.util.common.serializer.SerializeUtil;

public class RedisProtostuffMemoryMapper<KEY, MODEL extends UniqueModel<KEY>> extends RedisBinaryMemoryMapper<KEY, MODEL> {
	
	public RedisProtostuffMemoryMapper(String redisKey) {
		super(redisKey);
	}
	
	@Override
	protected byte[] serial(MODEL model) {
		return SerializeUtil.ProtostuffUtil.serial(model);
	}
	
	@Override
	protected MODEL deserial(byte[] data) {
		return SerializeUtil.ProtostuffUtil.deserial(data, clazz);
	}
}
