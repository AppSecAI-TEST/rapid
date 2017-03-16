package org.rapid.data.storage.mapper.serializer;

import org.rapid.data.storage.db.Entity;
import org.rapid.util.common.SerializeUtil;
import org.rapid.util.exception.ConvertFailuerException;

/**
 * 将一个 Entity 转换成一个 json类型
 * 
 * @author ahab
 *
 * @param <DATA>
 */
public class JsonEntitySerializer<KEY, DATA extends Entity<KEY>> implements EntitySerializer<KEY, DATA, String> {
	
	@Override
	public String convert(DATA k) throws ConvertFailuerException {
		return SerializeUtil.JsonUtil.GSON.toJson(k);
	}
	
	@Override
	public DATA antiConvet(String t, Class<DATA> clazz) throws ConvertFailuerException {
		return SerializeUtil.JsonUtil.GSON.fromJson(t, clazz);
	}
}
