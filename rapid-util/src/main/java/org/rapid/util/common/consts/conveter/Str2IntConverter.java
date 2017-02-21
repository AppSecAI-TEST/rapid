package org.rapid.util.common.consts.conveter;

import org.rapid.util.common.consts.IntConst;
import org.rapid.util.exception.ConstConvertFailureException;

public class Str2IntConverter extends IntConst implements StrConstConverter<Integer> {
	
	public Str2IntConverter(String key) {
		super(key, 0);
	}
	
	public Str2IntConverter(int id, String key) {
		super(id, key, 0);
	}

	public Str2IntConverter(String key, int defaultValue) {
		super(key, defaultValue);
	}

	public Str2IntConverter(int id, String key, int defaultValue) {
		super(key, defaultValue);
	}

	@Override
	public Integer convert(String value) throws ConstConvertFailureException {
		try {
			return Integer.valueOf(value);
		} catch (NumberFormatException e) {
			throw new ConstConvertFailureException(this, e);
		}
	}
}
