package org.rapid.data.storage.mapper;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.rapid.data.storage.db.Dao;
import org.rapid.data.storage.db.Entity;
import org.rapid.data.storage.mapper.serializer.EntitySerializer;
import org.rapid.data.storage.redis.RedisTable;
import org.rapid.util.common.SerializeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <pre>
 * 一张数据库的表映射成一个 redis 的 hash，表示 one to one
 * one to one hash 一般都是持久 hash，因为一个 hash 中存储了所有的数据，因此不能做 expire 操作
 * </pre>
 * 
 * @author ahab
 *
 * @param <KEY>
 * @param <ENTITY>
 */
@SuppressWarnings("unchecked")
public class O2OMapper<KEY, ENTITY extends Entity<KEY>, T, DAO extends Dao<KEY, ENTITY>> extends Mapper<KEY, ENTITY, DAO> {
	
	private static final Logger logger = LoggerFactory.getLogger(O2OMapper.class);
	
	protected T redisKey;
	protected Class<?> clazz;
	protected EntitySerializer<KEY, ENTITY, T> serializer;
	
	protected O2OMapper(RedisTable<KEY, ENTITY> table, T redisKey) {
		super(table);
		this.redisKey = redisKey;
		Type superType = getClass().getGenericSuperclass();   
		Type[] generics = ((ParameterizedType) superType).getActualTypeArguments();   
		clazz = (Class<?>) generics[2];
		if (clazz != byte[].class && clazz != String[].class) {
			logger.error("Error redis serial type : {}", clazz);
			throw new RuntimeException("Redis mapper failure!");
		}
	}
	
	@Override
	public void init() {
		List<ENTITY> entities = dao.selectAll();
		if (clazz == byte[].class) {				// 序列化成字节数组
			if (entities.isEmpty()) {
				redis.del((byte[]) redisKey);
				return;
			}
			byte[][] params = new byte[entities.size() * 2 + 1][];
			int index = 0;
			params[index++] = (byte[]) redisKey;
			for (ENTITY entity : entities) {
				params[index++] = SerializeUtil.RedisUtil.encode(entity.key().toString());
				params[index++] = (byte[]) serializer.convert(entity);
			}
			redis.delAndHmset(params);
		} else {			// 序列化成字符串
			if (entities.isEmpty()) {
				redis.del((String) redisKey);
				return;
			}
			String[] params = new String[entities.size() * 2 + 1];
			int index = 0;
			params[index++] = (String) redisKey;
			for (ENTITY entity : entities) {
				params[index++] = entity.key().toString();
				params[index++] = (String) serializer.convert(entity);
			}
			redis.delAndHmset(params);
		}
	}
	
	public Map<KEY, ENTITY> getAll() { 
		Map<KEY, ENTITY> map = new HashMap<KEY, ENTITY>();
		List<T> list = null;
		if (clazz == byte[].class) 
			list = (List<T>) redis.hvals((byte[]) redisKey);
		else 
			list = (List<T>) redis.hvals((String) redisKey);
		for (T value : list) {
			ENTITY entity = serializer.antiConvet(value, table.entityClass());
			map.put(entity.key(), entity);
		}
		return map;
	}
	
	/**
	 * 默认的 insert 仅仅将数据插入到 db 然后写入到对应的 hash 中，field 就是 主键 key
	 * 如果有些表再 redis 中有多重存储形式，则需要重写该方法
	 * 
	 */
	@Override
	public void insert(ENTITY entity) {
		dao.insert(entity);
		refresh(entity);
	}
	
	@Override
	public void update(ENTITY entity) {
		dao.update(entity);
		refresh(entity);
	}
	
	@Override
	public ENTITY getByKey(KEY key) {
		T data = clazz == byte[].class ? 
				(T) redis.hget((byte[]) redisKey, SerializeUtil.RedisUtil.encode(key.toString())) : 
					(T) redis.hget((String) redisKey, key.toString());
		if (null != data) 
			return serializer.antiConvet(data, table.entityClass());
		ENTITY entity = dao.selectByKey(key);
		if (null != entity)
			refresh(entity);
		return entity;
	}
	
	/**
	 * 只刷新缓存：默认只同步对应的 hash 中的数据，如果一张表能产生多分数据，则所有关联的数据都需要刷新
	 * 
	 * @param entity
	 */
	protected void refresh(ENTITY entity) { 
		if (clazz == byte[].class)
			redis.hset((byte[]) redisKey, SerializeUtil.RedisUtil.encode(entity.key().toString()), (byte[]) serializer.convert(entity));
		else
			redis.hset((String) redisKey, entity.key().toString(), (String) serializer.convert(entity));
	}
	
	public void setSerializer(EntitySerializer<KEY, ENTITY, T> serializer) {
		this.serializer = serializer;
	}
}
