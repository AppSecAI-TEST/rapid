package org.rapid.data.storage.mapper;

import org.rapid.data.storage.db.Dao;
import org.rapid.data.storage.db.Table;
import org.rapid.util.common.model.UniqueModel;
import org.rapid.util.common.serializer.SerializeUtil;

/**
 * 使用 protostuff 进行对象的序列化和反序列化
 * 
 * @author ahab
 *
 * @param <KEY>
 * @param <ENTITY>
 * @param <DAO>
 */
public class ProtostuffDBMapper<KEY, ENTITY extends UniqueModel<KEY>, DAO extends Dao<KEY, ENTITY>> extends BinaryDBMapper<KEY, ENTITY, DAO> {

	public ProtostuffDBMapper(Table table, String redisKey) {
		super(table, redisKey);
	}

	@Override
	protected byte[] serial(ENTITY entity) {
		return SerializeUtil.ProtostuffUtil.serial(entity);
	}
	
	@Override
	protected ENTITY deserial(byte[] data) {
		return SerializeUtil.ProtostuffUtil.deserial(data, clazz);
	}
}
