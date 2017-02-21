package org.rapid.util.common.consts;

public class IntConst extends ConstImpl<Integer> {

	public IntConst(String key) {
		super(key, 0);
	}
	
	public IntConst(int id, String key) {
		super(id, key, 0);
	}

	public IntConst(String key, int value) {
		super(key, value);
	}

	public IntConst(int id, String key, int value) {
		super(key, value);
	}
}
