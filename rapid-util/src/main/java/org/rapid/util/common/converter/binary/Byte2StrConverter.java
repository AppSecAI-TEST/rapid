package org.rapid.util.common.converter.binary;

import org.rapid.util.common.Consts;
import org.rapid.util.common.converter.BinaryConverter;
import org.rapid.util.exception.ConvertFailuerException;

public class Byte2StrConverter implements BinaryConverter<String> {
	
	public static final Byte2StrConverter INSTANCE			= new Byte2StrConverter();
	
	private Byte2StrConverter() {}

	@Override
	public String convert(byte[] k) throws ConvertFailuerException {
		return new String(k, Consts.UTF_8);
	}
}
