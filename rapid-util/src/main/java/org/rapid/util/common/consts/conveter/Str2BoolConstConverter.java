package org.rapid.util.common.consts.conveter;

import org.rapid.util.common.consts.BoolConst;
import org.rapid.util.exception.ConstConvertFailureException;

public class Str2BoolConstConverter extends BoolConst implements StrConstConverter<Boolean> {

	public Str2BoolConstConverter(String key) {
		super(key, false);
	}
	
	public Str2BoolConstConverter(int id, String key) {
		super(id, key, false);
	}

	public Str2BoolConstConverter(String key, boolean defaultValue) {
		super(key, defaultValue);
	}

	public Str2BoolConstConverter(int id, String key, boolean defaultValue) {
		super(id, key, defaultValue);
	}

	@Override
	public Boolean convert(String value) throws ConstConvertFailureException {
		return Boolean.valueOf(value);
	}
}
