package org.rapid.util.common.consts.conveter;

import org.rapid.util.common.consts.ObjConst;

public abstract class Str2ObjConstConverter<T> extends ObjConst<T> implements StrConstConverter<T> {

	public Str2ObjConstConverter(String key) {
		super(key, null);
	}
	
	public Str2ObjConstConverter(int id, String key) {
		super(id, key, null);
	}

	public Str2ObjConstConverter(String key, T defaultValue) {
		super(key, defaultValue);
	}

	public Str2ObjConstConverter(int id, String key, T defaultValue) {
		super(id, key, defaultValue);
	}
}
