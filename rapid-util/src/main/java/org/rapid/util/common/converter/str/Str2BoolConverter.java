package org.rapid.util.common.converter.str;

import org.rapid.util.common.converter.StrConverter;
import org.rapid.util.exception.ConvertFailuerException;

public class Str2BoolConverter implements StrConverter<Boolean> {
	
	public static final Str2BoolConverter INSTANCE = new Str2BoolConverter();

	@Override
	public Boolean convert(String value) throws ConvertFailuerException {
		return Boolean.valueOf(value);
	}
}
