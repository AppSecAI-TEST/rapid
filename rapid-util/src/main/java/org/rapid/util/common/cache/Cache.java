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
public interface Cache<ID, VALUE> {
	
	String name();

	void load() throws Exception;
	
	void reload() throws Exception;
	
	void dispose();
	
	VALUE getById(ID id);
	
	List<VALUE> getAll();
	
	List<VALUE> getByProperties(String property, Object value);
	
	List<VALUE> getByProperties(Map<String, Object> params);
}
