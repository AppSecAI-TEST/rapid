package org.rapid.data.storage.mapper;

import org.rapid.data.storage.db.Dao;
import org.rapid.data.storage.db.Table;
import org.rapid.util.common.model.UniqueModel;

/**
 * Redis 数据库映射器，将数据库表映射存储到 redis 中
 * 
 * @author ahab
 *
 * @param <KEY>
 * @param <ENTITY>
 */
public abstract class DBMapper<KEY, ENTITY extends UniqueModel<KEY>, DAO extends Dao<KEY, ENTITY>> extends MemoryMapper<KEY, ENTITY> {
	
	protected DAO dao;
	protected Table table;
	
	protected DBMapper(Table table, String redisKey) {
		super(redisKey);
		this.table = table;
	}
	
	@Override
	public ENTITY insert(ENTITY entity) {
		dao.insert(entity);
		flush(entity);
		return entity;
	}
	
	@Override
	public ENTITY getByKey(KEY key) {
		ENTITY entity = super.getByKey(key);
		if (null != entity)
			return entity;
		entity = dao.selectByKey(key);
		if (null != entity)
			flush(entity);
		return entity;
	}
	
	@Override
	public void update(ENTITY entity) {
		dao.update(entity);
		flush(entity);
	}
	
	/**
	 * 刷新数据
	 * 
	 * @param entity
	 */
	protected void flush(ENTITY entity) {
		super.update(entity);
	}
	
	public void setDao(DAO dao) {
		this.dao = dao;
	}
}
