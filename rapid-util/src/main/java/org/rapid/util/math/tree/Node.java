package org.rapid.util.math.tree;

import java.io.Serializable;

/**
 * 表示一个节点
 * 
 * @author ahab
 */
public interface Node<ID> extends Serializable {
	
	final int ROOT_LAYER				= 1;

	/**
	 * 本节点的唯一ID(在一棵树种，该ID必须唯一)
	 * 
	 * @return
	 */
	ID getId();
	
	/**
	 * 该节点所在的层级
	 * 
	 * @return
	 */
	int getLayer();
	
	/**
	 * 父节点的 ID
	 * 
	 * @return
	 */
	ID getParentId();
}
