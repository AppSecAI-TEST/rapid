package org.rapid.util.common.converter.str;

import org.rapid.util.common.converter.StrConverter;
import org.rapid.util.exception.ConvertFailuerException;

public class Str2ByteConverter implements StrConverter<Byte> {
	
	public static final Str2ByteConverter INSTANCE = new Str2ByteConverter();

	@Override
	public Byte convert(String value) throws ConvertFailuerException {
		try {
			return Byte.valueOf(value);
		} catch (NumberFormatException e) {
			throw new ConvertFailuerException(value, "byte", value + " can not cast into byte!");
		}
	}
}
