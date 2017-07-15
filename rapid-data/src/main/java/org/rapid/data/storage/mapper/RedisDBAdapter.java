package org.rapid.data.storage.mapper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.rapid.util.common.model.UniqueModel;
import org.rapid.util.common.serializer.Serializer;
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
	protected final String CACHE_CONTROLLER_KEY						= "hash:cache:controller";
	protected final String FULL_CACHE_CONTROLLER;
	
	protected RedisDBAdapter(Serializer<ENTITY, byte[]> serializer, String redisKey) {
		this(serializer, redisKey, null);
	}
	
	protected RedisDBAdapter(Serializer<ENTITY, byte[]> serializer, String redisKey, String fullCacheController) {
		super(serializer, redisKey);
		this.FULL_CACHE_CONTROLLER = fullCacheController;
	}
	
	@Override
	public void insert(ENTITY entity) {
		dao.insert(entity);
		flush(entity);
	}
	
	@Override
	public Map<KEY, ENTITY> getAll() {
		return super.getAll();
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
		List<byte[]> list = redis.hmget(redisKey, keys);
		Map<KEY, ENTITY> entities = new HashMap<KEY, ENTITY>();
		Iterator<KEY> iterator = keys.iterator();
		int index = 0;
		while (iterator.hasNext()) {
			iterator.next();
			byte[] data = list.get(index++);
			if (null != data) {
				iterator.remove();
				ENTITY entity = serializer.antiConvet(data);
				entities.put(entity.key(), entity);
			}
		}
		if (!keys.isEmpty()) {
			Map<KEY, ENTITY> temp = dao.getByKeys(keys);
			entities.putAll(temp);
			flush(new ArrayList<ENTITY>(temp.values()));
		}
		return entities;
	}
	
	@Override
	public Map<KEY, ENTITY> getByProperties(Map<String, Object> properties) {
		return super.getByProperties(properties);
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
	
	public void delete(ENTITY model) {
		remove(model);
		dao.delete(model.key());
	}
	
	/**
	 * 检测是否全部加载入内存
	 * 
	 * @param cacheControllerKey
	 * @param cacheControllerField
	 */
	protected void checkLoad(String cacheControllerKey, String cacheControllerField) {
		if (null == cacheControllerField)
			throw new UnsupportedOperationException("Unsupport full cache load!");
		if (!redis.hsetnx(cacheControllerKey, cacheControllerField, StringUtil.EMPTY))
			return;
		Map<KEY, ENTITY> map = dao.getAll();
		if (map.isEmpty())
			return;
		flush(new ArrayList<ENTITY>(map.values()));
	}
	
	public void setDao(DAO dao) {
		this.dao = dao;
	}
}
