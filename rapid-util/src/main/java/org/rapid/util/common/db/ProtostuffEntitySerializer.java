package org.rapid.util.common.db;

import org.rapid.util.common.SerializeUtil;
import org.rapid.util.exception.ConvertFailuerException;

public class ProtostuffEntitySerializer<KEY, DATA extends Entity<KEY>> implements EntitySerializer<KEY, DATA, byte[]> {

	@Override
	public DATA antiConvet(byte[] t, Class<DATA> clazz) throws ConvertFailuerException {
		return SerializeUtil.ProtostuffUtil.deserial(t, clazz);
	}

	@Override
	public byte[] convert(DATA k) throws ConvertFailuerException {
		return SerializeUtil.ProtostuffUtil.serial(k);
	}
}
