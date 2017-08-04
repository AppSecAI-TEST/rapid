package org.rapid.util.math.tree;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * 节点文档：Node 本身只是记录自己的数据，而每一个 docuement 不仅有本节点的 Node，还有所有其子节点的 Node
 * 
 * @author ahab
 */
public abstract class Document<ID, NODE extends Node<ID>, NOCUMENT extends Document<ID, NODE, NOCUMENT>> implements Serializable {
	
	private static final long serialVersionUID = 2463356307842730468L;
	
	protected NODE node;
	protected NOCUMENT parent;
	protected Map<ID, NOCUMENT> children;
	
	protected Document(NODE node) {
		this.node = node;
	}
	
	/**
	 * 返回本身的节点
	 * 
	 * @return
	 */
	public NODE node() {
		return node;
	}
	
	/**
	 * 返回父节点
	 * 
	 * @return
	 */
	public NOCUMENT parent() {
		return parent;
	}
	
	/**
	 * 返回该节点对应的所有子节点
	 * 
	 * @return
	 */
	public Map<ID, NOCUMENT> children() {
		return children;
	}
	
	public void addChild(NOCUMENT document) {
		if (null == this.children)
			this.children = new HashMap<ID, NOCUMENT>();
		this.children.put(document.node.getId(), document);
	}
}
