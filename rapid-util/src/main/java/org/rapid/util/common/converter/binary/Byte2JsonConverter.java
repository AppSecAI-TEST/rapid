package org.rapid.util.common.converter.binary;

import org.rapid.util.common.Consts;
import org.rapid.util.common.converter.BinaryConverter;
import org.rapid.util.common.serializer.SerializeUtil;
import org.rapid.util.exception.ConvertFailuerException;

public class Byte2JsonConverter<T> implements BinaryConverter<T> {
	
	private Class<T> clazz;
	
	public Byte2JsonConverter(Class<T> clazz) {
		this.clazz = clazz;
	}

	@Override
	public T convert(byte[] k) throws ConvertFailuerException {
		return SerializeUtil.JsonUtil.GSON.fromJson(new String(k, Consts.UTF_8), clazz);
	}
}
