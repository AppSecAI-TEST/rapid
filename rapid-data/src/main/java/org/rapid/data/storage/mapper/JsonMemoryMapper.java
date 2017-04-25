package org.rapid.data.storage.mapper;

import org.rapid.util.common.model.UniqueModel;
import org.rapid.util.common.serializer.SerializeUtil;

public class JsonMemoryMapper<KEY, MODEL extends UniqueModel<KEY>> extends MemoryMapper<String, KEY, MODEL>{

	public JsonMemoryMapper(String redisKey) {
		super(redisKey);
	}

	@Override
	protected String serial(MODEL model) {
		return SerializeUtil.JsonUtil.GSON.toJson(model);
	}

	@Override
	protected MODEL deserial(String data) {
		return SerializeUtil.JsonUtil.GSON.fromJson(data, clazz);
	}
}
