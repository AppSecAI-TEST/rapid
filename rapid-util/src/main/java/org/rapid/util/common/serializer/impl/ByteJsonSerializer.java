package org.rapid.util.common.serializer.impl;

import org.rapid.util.common.Consts;
import org.rapid.util.common.serializer.SerializeUtil;
import org.rapid.util.common.serializer.Serializer;
import org.rapid.util.exception.ConvertFailuerException;

public class ByteJsonSerializer<T> implements Serializer<byte[], T> {
	
	private Class<T> clazz;
	
	@Override
	public T convert(byte[] k) throws ConvertFailuerException {
		return SerializeUtil.JsonUtil.GSON.fromJson(new String(k, Consts.UTF_8), clazz);
	}

	@Override
	public byte[] antiConvet(T t) throws ConvertFailuerException {
		return SerializeUtil.JsonUtil.GSON.toJson(t).getBytes(Consts.UTF_8);
	}
	
	public void setClazz(Class<T> clazz) {
		this.clazz = clazz;
	}
}
