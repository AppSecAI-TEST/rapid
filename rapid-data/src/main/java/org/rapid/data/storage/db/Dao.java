package org.rapid.data.storage.db;

import java.util.List;

import org.rapid.util.common.model.UniqueModel;

public interface Dao<KEY, ENTITY extends UniqueModel<KEY>> {
	
	/**
	 * 插入数据
	 * 
	 * @param entity
	 */
	void insert(ENTITY entity);
	
	/**
	 * 读取所有的表数据
	 * 
	 * @return
	 */
	List<ENTITY> selectAll();
	
	/**
	 * 读取表中主键在指定的列表中的数据
	 * 
	 * @return
	 */
	List<ENTITY> selectWithinKey(List<KEY> keys);

	/**
	 * 通过主键获取唯一数据
	 * 
	 * @param key
	 * @return
	 */
	ENTITY selectByKey(KEY key);
	
	/**
	 * 更新数据
	 * 
	 * @param entity
	 */
	void update(ENTITY entity);
}
