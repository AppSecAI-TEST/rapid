package org.rapid.util.common.converter.str;

import org.rapid.util.common.converter.StrConverter;
import org.rapid.util.exception.ConvertFailuerException;

public class Str2LongConverter implements StrConverter<Long> {
	
	public static final Str2LongConverter INSTANCE = new Str2LongConverter();

	@Override
	public Long convert(String value) throws ConvertFailuerException {
		try {
			return Long.valueOf(value);
		} catch (NumberFormatException e) {
			throw new ConvertFailuerException(value, "long", value + " can not cast into long!");
		}
	}
}
