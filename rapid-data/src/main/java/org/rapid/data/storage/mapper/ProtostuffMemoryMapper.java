package org.rapid.data.storage.mapper;

import org.rapid.util.common.model.UniqueModel;
import org.rapid.util.common.serializer.SerializeUtil;

public class ProtostuffMemoryMapper<KEY, MODEL extends UniqueModel<KEY>> extends MemoryMapper<byte[], KEY, MODEL> {
	
	public ProtostuffMemoryMapper(String redisKey) {
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
