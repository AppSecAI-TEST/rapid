package org.rapid.util.common.serializer.impl;

import org.rapid.util.common.Consts;
import org.rapid.util.common.serializer.Serializer;
import org.rapid.util.exception.ConvertFailuerException;

public class ByteRawSerializer implements Serializer<String, byte[]> {
	
	public static final ByteRawSerializer INSTANCE				= new ByteRawSerializer();
	
	private ByteRawSerializer() {}
	
	@Override
	public byte[] convert(String k) throws ConvertFailuerException {
		return k.getBytes(Consts.UTF_8);
	}

	@Override
	public String antiConvet(byte[] t) throws ConvertFailuerException {
		return new String(t, Consts.UTF_8);
	}
	
	@Override
	public void setClazz(Class<String> clazz) {
		throw new UnsupportedOperationException("ByteRawSerializer can not set class type!");
	}
}
