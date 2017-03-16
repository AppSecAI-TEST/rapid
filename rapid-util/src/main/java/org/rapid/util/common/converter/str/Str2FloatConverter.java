package org.rapid.util.common.converter.str;

import org.rapid.util.common.converter.StrConverter;
import org.rapid.util.exception.ConvertFailuerException;

public class Str2FloatConverter implements StrConverter<Float> {
	
	public static final Str2FloatConverter INSTANCE = new Str2FloatConverter();

	@Override
	public Float convert(String value) throws ConvertFailuerException {
		try {
			return Float.valueOf(value);
		} catch (NumberFormatException e) {
			throw new ConvertFailuerException(value, "float", value + " can not cast into float!");
		}
	}
}
