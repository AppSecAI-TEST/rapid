package org.rapid.util.common.serializer.impl;

import org.rapid.util.common.Consts;
import org.rapid.util.common.serializer.Serializer;
import org.rapid.util.exception.ConvertFailuerException;

public class ByteRawSerializer implements Serializer<byte[], String> {
	
	public static final ByteRawSerializer INSTANCE				= new ByteRawSerializer();
	
	private ByteRawSerializer() {}
	
	@Override
	public String convert(byte[] k) throws ConvertFailuerException {
		return new String(k, Consts.UTF_8);
	}

	@Override
	public byte[] antiConvet(String t) throws ConvertFailuerException {
		return t.getBytes(Consts.UTF_8);
	}
}
