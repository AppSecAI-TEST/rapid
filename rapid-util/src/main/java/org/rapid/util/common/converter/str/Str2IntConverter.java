package org.rapid.util.common.converter.str;

import org.rapid.util.common.converter.StrConverter;
import org.rapid.util.exception.ConvertFailuerException;

public class Str2IntConverter implements StrConverter<Integer> {
	
	public static final Str2IntConverter INSTANCE = new Str2IntConverter();
	
	@Override
	public Integer convert(String value) throws ConvertFailuerException {
		try {
			return Integer.valueOf(value);
		} catch (NumberFormatException e) {
			throw new ConvertFailuerException(value, "integer", value + " can not cast into integer!");
		}
	}
}
