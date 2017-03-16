package org.rapid.util.common.consts;

public class StrConst extends ConstImpl<String> {

	public StrConst(String key) {
		super(key, null);
	}
	
	public StrConst(int id, String key) {
		super(id, key, null);
	}

	public StrConst(String key, String value) {
		super(key, value);
	}

	public StrConst(int id, String key, String value) {
		super(id, key, value);
	}
}
