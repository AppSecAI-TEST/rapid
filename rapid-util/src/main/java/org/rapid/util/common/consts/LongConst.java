package org.rapid.util.common.consts;

public class LongConst extends ConstImpl<Long> {

	public LongConst(String key) {
		super(key, 0l);
	}
	
	public LongConst(int id, String key) {
		super(id, key, 0l);
	}

	public LongConst(String key, long value) {
		super(key, value);
	}

	public LongConst(int id, String key, long defaultValue) {
		super(key, defaultValue);
	}
}
