package org.rapid.util.common.db;

import java.util.HashMap;
import java.util.Map;

import org.rapid.util.common.key.Key;

/**
 * 表示数据库的表
 * 
 * @author ahab
 *
 * @param <DATA> 表示具体表中的数据对象
 */
public abstract class Table<KEY, DATA extends Entity<KEY>> {
	
	private String key;
	private Map<Key<Integer>, EntitySerializer<KEY, DATA, ?>> serializers;
	
	protected Table(String key) {
		this.key = key;
		this.serializers = new HashMap<Key<Integer>, EntitySerializer<KEY, DATA, ?>>();
		initSerializers();
	}

	public String key() {
		return this.key;
	}
	
	/**
	 * 如果表中数据需要存入 redis 则需要 redis 对应的 key
	 */
	public String redisKey() {
		// do nothing
		return null;
	}
	
	/**
	 * 初始化 {@link EntitySerializer}
	 * 
	 */
	protected abstract void initSerializers();
	
	protected void addSerializer(EntitySerializer<KEY, DATA, ?> serializer) {
		serializers.put(serializer.identity(), serializer);
	}
	
	/**
	 * 获取对象转换器，如果没有指定类型的对象转换器则返回 null
	 * 
	 * @param type
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public <VALUE> EntitySerializer<KEY, DATA, VALUE> getEntitySerializer(Key<Integer> key) {
		return (EntitySerializer<KEY, DATA, VALUE>) serializers.get(key);
	}
}
