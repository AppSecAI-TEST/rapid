package org.rapid.util.common.consts;

public class BoolConst extends ConstImpl<Boolean> {
	
	public BoolConst(String key) {
		super(key, false);
	}
	
	public BoolConst(int id, String key) {
		super(id, key, false);
	}

	public BoolConst(String key, boolean value) {
		super(key, value);
	}

	public BoolConst(int id, String key, boolean defaultValue) {
		super(key, defaultValue);
	}
}
