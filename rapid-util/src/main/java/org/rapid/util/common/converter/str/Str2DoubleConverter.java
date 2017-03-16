package org.rapid.util.common.converter.str;

import org.rapid.util.common.converter.StrConverter;
import org.rapid.util.exception.ConvertFailuerException;

public class Str2DoubleConverter implements StrConverter<Double> {
	
	public static final Str2DoubleConverter INSTANCE = new Str2DoubleConverter();

	@Override
	public Double convert(String value) throws ConvertFailuerException {
		try {
			return Double.valueOf(value);
		} catch (NumberFormatException e) {
			throw new ConvertFailuerException(value, "double", value + " can not cast into double!");
		}
	}
}
