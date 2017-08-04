package org.rapid.util.math.tree.mptt;

import org.rapid.util.math.tree.Document;

public abstract class MPTTNodeDocument<ID, NODE extends MPTTNode<ID>, NOCUMENT extends Document<ID, NODE, NOCUMENT>> extends Document<ID, NODE, NOCUMENT> {

	private static final long serialVersionUID = -8328617800639518564L;

	protected MPTTNodeDocument(NODE node) {
		super(node);
	}
}
