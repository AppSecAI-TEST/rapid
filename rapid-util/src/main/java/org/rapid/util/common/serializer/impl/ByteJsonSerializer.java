package org.rapid.util.common.serializer.impl;

import org.rapid.util.common.Consts;
import org.rapid.util.common.serializer.SerializeUtil;
import org.rapid.util.common.serializer.Serializer;
import org.rapid.util.exception.ConvertFailuerException;

public class ByteJsonSerializer<T> implements Serializer<T, byte[]> {
	
	private Class<T> clazz;
	
	@Override
	public byte[] convert(T k) throws ConvertFailuerException {
		return SerializeUtil.JsonUtil.GSON.toJson(k).getBytes(Consts.UTF_8);
	}

	@Override
	public T antiConvet(byte[] t) throws ConvertFailuerException {
		return SerializeUtil.JsonUtil.GSON.fromJson(new String(t, Consts.UTF_8), clazz);
	}

	@Override
	public void setClazz(Class<T> clazz) {
		this.clazz = clazz;
	}
}
