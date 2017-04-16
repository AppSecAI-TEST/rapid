package org.rapid.data.storage.mapper.serializer;

import org.rapid.util.common.model.UniqueModel;
import org.rapid.util.common.serializer.SerializeUtil;
import org.rapid.util.exception.ConvertFailuerException;

public class ProtostuffEntitySerializer<KEY, DATA extends UniqueModel<KEY>> implements EntitySerializer<KEY, DATA, byte[]> {
	
	public static final ProtostuffEntitySerializer<Object, UniqueModel<Object>> INSTANCE = new ProtostuffEntitySerializer<>();
	
	@Override
	public DATA antiConvet(byte[] t, Class<DATA> clazz) throws ConvertFailuerException {
		return SerializeUtil.ProtostuffUtil.deserial(t, clazz);
	}

	@Override
	public byte[] convert(DATA k) throws ConvertFailuerException {
		return SerializeUtil.ProtostuffUtil.serial(k);
	}
}
