package org.rapid.util.common.converter.str;

import org.rapid.util.common.converter.StrConverter;
import org.rapid.util.exception.ConvertFailuerException;

public class Str2StrConverter implements StrConverter<String> {
	
	public static final Str2StrConverter INSTANCE = new Str2StrConverter();

	@Override
	public String convert(String value) throws ConvertFailuerException {
		return value;
	}
}
