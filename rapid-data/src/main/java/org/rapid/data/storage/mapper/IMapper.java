package org.rapid.data.storage.mapper;

import org.rapid.data.storage.db.Entity;

/**
 * 数据库映射器接口，能够将数据库中的表数据映射到指定的地方存储：比如 redis、memory 等
 * 
 * @author ahab
 *
 * @param <KEY>		主键类型
 * @param <ENTITY>	表所对应的 java pojo 类型
 */
public interface IMapper<KEY, ENTITY extends Entity<KEY>>  {

	/**
	 * 初始化，可以将一些读多写少的数据先进行映射，比如配置数据
	 */
	void init();
	
	/**
	 * 插入数据
	 * 
	 * @param entity
	 */
	void insert(ENTITY entity);
	
	/**
	 * 根据主键获取唯一的数据
	 * 
	 * @return
	 */
	ENTITY getByKey(KEY key);
	
	/**
	 * 更新数据
	 * 
	 * @param entity
	 */
	void update(ENTITY entity);
}
