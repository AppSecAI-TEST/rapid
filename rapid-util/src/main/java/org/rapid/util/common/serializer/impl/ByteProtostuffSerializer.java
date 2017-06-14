package org.rapid.util.common.serializer.impl;

import org.rapid.util.common.serializer.SerializeUtil;
import org.rapid.util.common.serializer.Serializer;
import org.rapid.util.exception.ConvertFailuerException;

public class ByteProtostuffSerializer<T> implements Serializer<byte[], T> {
	
	private Class<T> clazz;

	@Override
	public T convert(byte[] k) throws ConvertFailuerException {
		return SerializeUtil.ProtostuffUtil.deserial(k, clazz);
	}

	@Override
	public byte[] antiConvet(T t) throws ConvertFailuerException {
		return SerializeUtil.ProtostuffUtil.serial(t);
	}
	
	public void setClazz(Class<T> clazz) {
		this.clazz = clazz;
	}
}
