package org.rapid.data.storage.mapper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.rapid.data.storage.db.Dao;
import org.rapid.data.storage.db.Table;
import org.rapid.util.common.model.UniqueModel;
import org.rapid.util.common.serializer.SerializeUtil;

/**
 * Redis 数据库映射器，将数据库表映射存储到 redis 中
 * 
 * @author ahab
 *
 * @param <KEY>
 * @param <ENTITY>
 */
public abstract class BinaryDBMapper<KEY, ENTITY extends UniqueModel<KEY>, DAO extends Dao<KEY, ENTITY>> extends BinaryMemoryMapper<KEY, ENTITY> {
	
	protected DAO dao;
	protected Table table;
	
	protected BinaryDBMapper(Table table, String redisKey) {
		super(redisKey);
		this.table = table;
	}
	
	@Override
	public ENTITY insert(ENTITY entity) {
		dao.insert(entity);
		flush(entity);
		return entity;
	}
	
	@Override
	public ENTITY getByKey(KEY key) {
		ENTITY entity = super.getByKey(key);
		if (null != entity)
			return entity;
		entity = dao.selectByKey(key);
		if (null != entity)
			flush(entity);
		return entity;
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<ENTITY> getWithinKey(List<KEY> keys) {
		if (null == keys || keys.isEmpty())
			return Collections.EMPTY_LIST;
		byte[][] fields = new byte[keys.size()][];
		int index = 0;
		for (KEY key : keys)
			fields[index++] = SerializeUtil.RedisUtil.encode(key);
		List<byte[]> datas = redis.hmget(redisKey, fields);
		List<KEY> missed = new ArrayList<KEY>();
		List<ENTITY> entities = new ArrayList<ENTITY>();
		for (int i = 0, len = datas.size(); i < len; i++) {
			byte[] data = datas.get(i);
			if (null == data)
				missed.add(keys.get(i));
			else
				entities.add(deserial(data));
		}
		if (!missed.isEmpty()) {
			List<ENTITY> list = dao.selectWithinKey(keys);
			entities.addAll(list);
			flush(list);
		}
		return entities;
	}
	
	@Override
	public void update(ENTITY entity) {
		dao.update(entity);
		flush(entity);
	}
	
	/**
	 * 刷新数据
	 * 
	 * @param entity
	 */
	public void flush(ENTITY entity) {
		super.update(entity);
	}
	
	public void flush(List<ENTITY> entities) {
		redis.hmsetProtostuff(redisKey, entities);
	}
	
	public void setDao(DAO dao) {
		this.dao = dao;
	}
}
