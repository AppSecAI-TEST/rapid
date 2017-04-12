package org.rapid.util.common.cache;

import java.util.List;
import java.util.Map;

/**
 * 一级缓存，直接加载入内存，适合存放数据量不大、变动不频繁但是使用频繁的数据，比如说全局配置文件
 * 
 * @author ahab
 *
 * @param <ID> 缓存主键类型
 * @param <VALUE> 存储的对象类型
 */
public interface ICache<ID, VALUE> {
	
	/**
	 * 缓存的 priority 决定了再 cacheService 中该 Cache 的加载的顺序，如果
	 * 不指定默认为 0 则顺序是不定的，否则 priority 越大越先被加载
	 * 
	 * @return
	 */
	int priority();
	
	String name();

	void load() throws Exception;
	
	void reload() throws Exception;
	
	void dispose();
	
	VALUE getById(ID id);
	
	List<VALUE> getAll();
	
	List<VALUE> getByProperties(String property, Object value);
	
	List<VALUE> getByProperties(Map<String, Object> params);
}
