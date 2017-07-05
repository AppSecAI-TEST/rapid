package org.rapid.data.storage.mapper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.rapid.util.common.model.UniqueModel;
import org.rapid.util.common.serializer.Serializer;
import org.rapid.util.lang.CollectionUtils;

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
	
	protected RedisDBAdapter(Serializer<ENTITY, byte[]> serializer, String redisKey) {
		super(serializer, redisKey);
	}
	
	@Override
	public void insert(ENTITY entity) {
		dao.insert(entity);
		flush(entity);
	}
	
	@Override
	public List<ENTITY> getAll() {
		List<byte[]> list = redis.hvals(redisKey);
		if (CollectionUtils.isEmpty(list))
			return null;
		List<ENTITY> brands = new ArrayList<ENTITY>(list.size());
		for (byte[] data : list)
			brands.add(serializer.antiConvet(data));
		return brands;
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
	@SuppressWarnings("unchecked")
	public List<ENTITY> getWithinKey(List<KEY> keys) {
		if (null == keys || keys.isEmpty())
			return Collections.EMPTY_LIST;
		List<byte[]> list = redis.hmget(redisKey, keys);
		List<ENTITY> entities = new ArrayList<ENTITY>();
		Iterator<KEY> iterator = keys.iterator();
		int index = 0;
		while (iterator.hasNext()) {
			iterator.next();
			byte[] data = list.get(index++);
			if (null != data) {
				iterator.remove();
				entities.add(serializer.antiConvet(data));
			}
		}
		if (!keys.isEmpty()) {
			List<ENTITY> temp = dao.getWithinKey(keys);
			entities.addAll(temp);
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
		dao.delete(key);
		remove(key);
	}
	
	protected void checkLoad(String cacheControllerKey, String cacheControllerField) {
		if (!redis.hsetnx(cacheControllerKey, cacheControllerField, cacheControllerField))
			return;
		List<ENTITY> list = dao.getAll();
		if (list.isEmpty())
			return;
		flush(list);
	}
	
	public void setDao(DAO dao) {
		this.dao = dao;
	}
}
