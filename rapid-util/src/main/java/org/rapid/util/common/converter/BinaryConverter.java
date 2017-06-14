package org.rapid.util.common.converter;

import org.rapid.util.exception.ConvertFailuerException;

public interface BinaryConverter<T> extends Converter<byte[], T> {

	@Override
	T convert(byte[] k) throws ConvertFailuerException;
}
