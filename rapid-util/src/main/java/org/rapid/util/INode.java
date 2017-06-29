package org.rapid.util;

import java.util.Map;

/**
 * 节点
 * 
 * @author ahab
 */
public interface INode<NODE extends INode<?>> {

	/**
	 * 节点ID
	 * 
	 * @return
	 */
	String getId();
	
	/**
	 * 节点称号
	 * 
	 * @return
	 */
	String getTitle();
	
	/**
	 * 子节点
	 * 
	 * @return
	 */
	Map<String, NODE> getChildren();
}
