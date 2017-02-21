package org.rapid.util.common.consts;

/**
 * 常量
 * 
 * @author ahab
 *
 * @param <T>
 */
public interface Const<T> {

	/**
	 * 常量ID, 如果不需要则默认为 0, 如果定义了 id 则必须全局唯一
	 * 
	 * @return
	 */
	int id();
	
	/**
	 * 常量名
	 * 
	 * @return
	 */
	String key();
	
	/**
	 * 值
	 * 
	 * @return
	 */
	T value();
}
