package org.rapid.util.common.consts;

public class ObjConst<T> extends ConstImpl<T> {

	public ObjConst(String key) {
		super(key, null);
	}
	
	public ObjConst(int id, String key) {
		super(id, key, null);
	}

	public ObjConst(String key, T value) {
		super(key, value);
	}

	public ObjConst(int id, String key, T defaultValue) {
		super(id, key, defaultValue);
	}

}
