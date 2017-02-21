package org.rapid.util.common.consts.conveter;

import org.rapid.util.common.consts.BoolConst;
import org.rapid.util.exception.ConstConvertFailureException;

public class Str2BoolConverter extends BoolConst implements StrConstConverter<Boolean> {

	public Str2BoolConverter(String key) {
		super(key, false);
	}
	
	public Str2BoolConverter(int id, String key) {
		super(id, key, false);
	}

	public Str2BoolConverter(String key, boolean defaultValue) {
		super(key, defaultValue);
	}

	public Str2BoolConverter(int id, String key, boolean defaultValue) {
		super(key, defaultValue);
	}

	@Override
	public Boolean convert(String value) throws ConstConvertFailureException {
		return Boolean.valueOf(value);
	}
}
