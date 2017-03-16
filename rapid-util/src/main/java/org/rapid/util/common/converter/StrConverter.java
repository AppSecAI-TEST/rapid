package org.rapid.util.common.converter;

import org.rapid.util.exception.ConvertFailuerException;

public interface StrConverter<T> extends Converter<String, T> {

	@Override
	T convert(String k) throws ConvertFailuerException;
}
