package org.rapid.util.math.tree;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

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
			parallels.put(id, document);
			if (null != node.getParentId()) {
				for (Entry<ID, DOCUMENT> entry : parallels.entrySet()) {
					if (node.getParentId().equals(entry.getKey())) {
						entry.getValue().addChild(document);
						break;
					}
				}
			}
		}
		Iterator<DOCUMENT> iterator = parallels.values().iterator();
		while (iterator.hasNext()) {
			DOCUMENT document = iterator.next();
			if (null == document.node.getParentId())
				continue;
			iterator.remove();
		}
		return parallels;
	}
}
