package org.rapid.util.common.consts.conveter;

import org.rapid.util.common.consts.LongConst;
import org.rapid.util.exception.ConstConvertFailureException;

public class Str2LongConstConverter extends LongConst implements StrConstConverter<Long> {

	public Str2LongConstConverter(String key) {
		super(key, 0l);
	}
	
	public Str2LongConstConverter(int id, String key) {
		super(id, key, 0l);
	}

	public Str2LongConstConverter(String key, long defaultValue) {
		super(key, defaultValue);
	}

	public Str2LongConstConverter(int id, String key, long defaultValue) {
		super(id, key, defaultValue);
	}

	@Override
	public Long convert(String value) throws ConstConvertFailureException {
		try {
			return Long.valueOf(value);
		} catch (NumberFormatException e) {
			throw new ConstConvertFailureException(this, e);
		}
	}
}
