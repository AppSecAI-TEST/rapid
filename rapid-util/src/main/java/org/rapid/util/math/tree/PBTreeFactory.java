package org.rapid.util.math.tree;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Parent based tree factory ： 基于父子结构的 factory，根据当前节点的父节点来构造树，需要子类实现 instance 方法
 * 
 * @author ahab
 *
 * @param <ID>
 * @param <NODE>
 * @param <DOCUMENT>
 */
public abstract class PBTreeFactory<ID, NODE extends Node<ID>, DOCUMENT extends Document<ID, NODE, DOCUMENT>> implements TreeFactory<ID, NODE, DOCUMENT> {

	/**
	 * 该实现需要注意：前提是所有节点的 ID 都唯一，如果存在不同树有相同 ID 的节点，结果是不可预知的
	 */
	@Override
	public Map<ID, DOCUMENT> build(List<NODE> nodes) {
		Map<ID, DOCUMENT> parallels = new HashMap<ID, DOCUMENT>();
		for (NODE node : nodes) {
			ID id = node.getId();
			DOCUMENT document = instance(node);
			DOCUMENT child = parallels.get(id);
			if (null == child) 
				parallels.put(id, document);
			else {
				parallels.remove(id);
				document.addChild(child);
				if (null != node.getParentId())
					parallels.put(node.getParentId(), document);
			}
		}
		return parallels;
	}
}
