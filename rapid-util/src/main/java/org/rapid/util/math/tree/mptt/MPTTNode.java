package org.rapid.util.math.tree.mptt;

import org.rapid.util.math.tree.Node;
import org.rapid.util.math.tree.NodeImpl;

/**
 * 预排序遍历树节点：modified preorder tree traversal
 * 
 * @author ahab
 */
public class MPTTNode<ID> extends NodeImpl<ID> implements Node<ID> {
	
	private static final long serialVersionUID = 1256679475371107406L;
	
	public static final int INITIAL_ROOT_LEFT			= 0;
	
	protected int left;
	protected int right;

	/**
	 * 该节点的左值
	 * 
	 * @return
	 */
	public int getLeft() {
		return this.left;
	}
	
	public void setLeft(int left) {
		this.left = left;
	}
	
	/**
	 * 该节点的右只
	 * 
	 * @return
	 */
	public int getRight() {
		return this.right;
	}
	
	public void setRight(int right) {
		this.right = right;
	}
}
