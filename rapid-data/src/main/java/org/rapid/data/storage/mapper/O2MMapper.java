package org.rapid.data.storage.mapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.rapid.data.storage.db.Dao;
import org.rapid.data.storage.db.Entity;
import org.rapid.data.storage.redis.RedisTable;
import org.rapid.util.common.serializer.SerializeUtil;

/**
 * <pre>
 * 一条数据映射成一个 redis 的 hash，表示 one to many
 * </pre>
 * 
 * @author ahab
 *
 * @param <KEY>
 * @param <ENTITY>
 * @param <T>
 */
public abstract class O2MMapper<KEY, ENTITY extends Entity<KEY>, DAO extends Dao<KEY, ENTITY>>  extends Mapper<KEY, ENTITY, DAO> {
	
	protected O2MMapper(RedisTable<KEY, ENTITY> table) {
		super(table);
	}

	/**
	 * one to many 类型的数据一般适合大表且修改频繁，因此不适合全表导入 redis 中，默认初始化什么都不做
	 */
	@Override
	public void init() {
		// do nothing
	}
	
	/**
	 * 因为是一条数据对应一个 hash key，因此需要具体什么 key 需要子类来实现
	 * 
	 * @param entity
	 * @return
	 */
	protected abstract String redisKey(KEY key);
	
	@Override
	public ENTITY insert(ENTITY entity) {
		dao.insert(entity);
		refresh(entity);
		return entity;
	}
	
	/**
	 * 根据主键获取数据
	 * 
	 * @return
	 */
	@Override
	public ENTITY getByKey(KEY key) {
		ENTITY entity = null;
		List<String> list = redis.hgetAllAndRefresh(redisKey(key), table.expireMillisSeconds());
		if (null == list) {
			entity = dao.selectByKey(key);
			if (null != entity) 
				refresh(entity);
		} else 
			entity = parse(list);
		return entity;
	}

	@Override
	public void update(ENTITY entity) {
		dao.update(entity);
		refresh(entity);
	}
	
	protected ENTITY parse(List<String> list) {
		Map<String, String> map = new HashMap<String, String>(list.size() / 2);
		for (int i = 0, len = list.size(); i < len; i += 2) 
			map.put(list.get(i), list.get(i + 1));
		return SerializeUtil.BeanMapUtil.mapToBean(map, table.entityClass());
	}
	
	protected ENTITY parse(Map<String, String> map) {
		return SerializeUtil.BeanMapUtil.mapToBean(map, table.entityClass());
	}
	
	/**
	 * 默认只刷新实体数据的缓存，如果想要连带一些与该 ENTITY 有关的数据一起刷新那么子类重写该方法即可
	 * 
	 * @param entity
	 */
	protected void refresh(ENTITY entity) { 
		Map<String, String> map = SerializeUtil.BeanMapUtil.beanToMap(entity);
		redis.refreshHash(handlerLuaParameters(map, entity.key().toString(), String.valueOf(table.expireMillisSeconds())));
	}
	
	protected String[] handlerLuaParameters(Map<String, String> map, String... params) { 
		String[] arr = new String[map.size() * 2 + params.length];
		int index = 0;
		for (int len = params.length; index < len; index++)
			arr[index] = params[index];
		for (Entry<String, String> entry : map.entrySet()) {
			arr[index++] = entry.getKey();
			arr[index++] = entry.getValue();
		}
		return arr;
	}
}
