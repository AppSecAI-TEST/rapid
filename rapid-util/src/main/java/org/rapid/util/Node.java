package org.rapid.util;

import java.util.HashMap;
import java.util.Map;

public class Node<NODE extends Node<?>> implements INode<NODE> {
	
	protected String id;
	protected String title;
	protected Map<String, NODE> children;
	
	public Node() {}
	
	public Node(String id, String title) {
		this.id = id;
		this.title = title;
	}

	@Override
	public String getId() {
		return this.id;
	}

	@Override
	public String getTitle() {
		return this.title;
	}

	public void addChiled(NODE node) {
		if (null == children)
			this.children = new HashMap<String, NODE>();
		this.children.put(node.id, node);
	}
	
	public void removeChild(String id) { 
		if (null == this.children)
			return;
		this.children.remove(id);
		if (this.children.isEmpty())
			this.children = null;
	}
	
	public NODE getChild(String id) {
		return null == children ? null : children.get(id);
	}
	
	@Override
	public Map<String, NODE> getChildren() {
		return this.children;
	}
	
	public void setChildren(Map<String, NODE> children) {
		this.children = children;
	}
}
