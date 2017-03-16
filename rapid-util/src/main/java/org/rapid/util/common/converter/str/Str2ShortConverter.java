package org.rapid.util.common.converter.str;

import org.rapid.util.common.converter.StrConverter;
import org.rapid.util.exception.ConvertFailuerException;

public class Str2ShortConverter implements StrConverter<Short> {
	
	public static final Str2ShortConverter INSTANCE = new Str2ShortConverter();

	@Override
	public Short convert(String value) throws ConvertFailuerException {
		try {
			return Short.valueOf(value);
		} catch (NumberFormatException e) {
			throw new ConvertFailuerException(value, "short", value + " can not cast into short!");
		}
	}
}
