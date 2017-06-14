package org.rapid.util.common.converter.binary;

import java.util.Arrays;

import org.rapid.util.common.Consts;
import org.rapid.util.common.converter.BinaryConverter;
import org.rapid.util.exception.ConvertFailuerException;

public class Byte2RawIntConverter implements BinaryConverter<Integer> {
	
	public static final Byte2RawIntConverter INSTANCE			= new Byte2RawIntConverter();
	
	private Byte2RawIntConverter() {}

	@Override
	public Integer convert(byte[] k) throws ConvertFailuerException {
		try {
			return Integer.valueOf(new String(k, Consts.UTF_8));
		} catch (NumberFormatException e) {
			throw new ConvertFailuerException(Arrays.toString(k), "int", "can not cast to int");
		}
	}
}
