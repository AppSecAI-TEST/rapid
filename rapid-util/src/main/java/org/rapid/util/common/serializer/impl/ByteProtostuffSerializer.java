package org.rapid.util.common.serializer.impl;

import org.rapid.util.common.serializer.SerializeUtil;
import org.rapid.util.common.serializer.Serializer;
import org.rapid.util.exception.ConvertFailuerException;

public class ByteProtostuffSerializer<T> implements Serializer<T, byte[]> {
	
	private Class<T> clazz;
	
	@Override
	public byte[] convert(T k) throws ConvertFailuerException {
		return SerializeUtil.ProtostuffUtil.serial(k);
	}

	@Override
	public T antiConvet(byte[] t) throws ConvertFailuerException {
		return SerializeUtil.ProtostuffUtil.deserial(t, clazz);
	}
	
	@Override
	public void setClazz(Class<T> clazz) {
		this.clazz = clazz;
	}
}
