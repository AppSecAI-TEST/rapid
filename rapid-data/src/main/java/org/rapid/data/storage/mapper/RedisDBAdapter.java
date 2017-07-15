package org.rapid.data.storage.mapper;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.rapid.util.common.model.UniqueModel;
import org.rapid.util.common.serializer.Serializer;
import org.rapid.util.lang.CollectionUtil;
import org.rapid.util.lang.StringUtil;

/**
 * 将数据库中的数据映射到 redis 的适配器，其本身也是一个 Mapper
 * 
 * @author ahab
 *
 * @param <KEY>
 * @param <ENTITY>
 */
public abstract class RedisDBAdapter<KEY, ENTITY extends UniqueModel<KEY>, DAO extends DBMapper<KEY, ENTITY>> extends RedisMapper<KEY, ENTITY> {
	
	protected DAO dao;
	protected static final String CACHE_CONTROLLER_KEY						= "hash:cache:controller";
	
	protected RedisDBAdapter(Serializer<ENTITY, byte[]> serializer, String redisKey) {
		super(serializer, redisKey);
	}
	
	@Override
	public void insert(ENTITY entity) {
		dao.insert(entity);
		flush(entity);
	}
	
	@Override
	public Map<KEY, ENTITY> getAll() {
		Map<KEY, ENTITY> map = checkLoad();
		return null == map ? super.getAll() : map;
	}
	
	@Override
	public ENTITY getByKey(KEY key) {
		ENTITY entity = super.getByKey(key);
		if (null != entity)
			return entity;
		entity = dao.getByKey(key);
		if (null != entity)
			flush(entity);
		return entity;
	}
	
	@Override
	public Map<KEY, ENTITY> getByKeys(Collection<KEY> keys) {
		Map<KEY, ENTITY> entities = new HashMap<KEY, ENTITY>();
		if (CollectionUtil.isEmpty(keys))
			return entities;
		List<byte[]> list = redis.hmget(redisKey, keys);
		for (byte[] data : list) {
			if (null == data)
				continue;
			ENTITY entity = serializer.antiConvet(data);
			entities.put(entity.key(), entity);
		}
		keys.removeAll(entities.keySet());
		if (!keys.isEmpty()) {
			Map<KEY, ENTITY> temp = dao.getByKeys(keys);
			entities.putAll(temp);
			flush(temp);
		}
		return entities;
	}
	
	@Override
	public void update(ENTITY entity) {
		dao.update(entity);
		flush(entity);
	}
	
	@Override
	public void delete(KEY key) {
		ENTITY entity = getByKey(key);
		if (null == entity)
			return;
		remove(entity);
		dao.delete(key);
	}
	
	public void delete(ENTITY entity) {
		remove(entity);
		dao.delete(entity.key());
	}
	
	protected Map<KEY, ENTITY> checkLoad() {
		if (!redis.hsetnx(CACHE_CONTROLLER_KEY, redisKey, StringUtil.EMPTY))
			return null;
		Map<KEY, ENTITY> map = dao.getAll();
		if (!CollectionUtil.isEmpty(map))
			flush(map);
		return map;
	}
	
	protected boolean checkLoad(String controllerField) {
		return redis.hsetnx(CACHE_CONTROLLER_KEY, controllerField, StringUtil.EMPTY);
	}
	
	public void setDao(DAO dao) {
		this.dao = dao;
	}
}
