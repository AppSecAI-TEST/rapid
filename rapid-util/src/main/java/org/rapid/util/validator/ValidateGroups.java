package org.rapid.util.validator;

public interface ValidateGroups {
	
	interface CRUD{}

	/**
	 * 新增
	 * 
	 * @author ahab
	 */
	interface CREATE extends CRUD {}
	
	/**
	 * 修改
	 * 
	 * @author ahab
	 */
	interface UPDATE extends CRUD {}
	
	/**
	 * 删除
	 * 
	 * @author ahab
	 */
	interface DELETE extends CRUD {}
}
