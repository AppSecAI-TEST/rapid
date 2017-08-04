package org.rapid.util.math.tree;

import java.util.List;
import java.util.Map;

public interface TreeFactory<ID, NODE extends Node<ID>, DOCUMENT extends Document<ID, NODE, DOCUMENT>> {
	
	/**
	 * 构造 document 实例
	 * 
	 * @param node
	 * @return
	 */
	DOCUMENT instance(NODE node);

	/**
	 * 根据一组节点集合构建树结构：有可能是多棵树的节点
	 * 
	 * @param nodes
	 * @return
	 */
	Map<ID, DOCUMENT> build(List<NODE> nodes);
}
