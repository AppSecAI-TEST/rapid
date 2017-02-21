package org.rapid.util.common.consts.conveter;

import org.rapid.util.common.consts.StrConst;
import org.rapid.util.exception.ConstConvertFailureException;
import org.rapid.util.lang.StringUtils;

public class Str2StrConverter extends StrConst implements StrConstConverter<String> {

	public Str2StrConverter(String key) {
		super(key, null);
	}
	
	public Str2StrConverter(int id, String key) {
		super(id, key, null);
	}

	public Str2StrConverter(String key, String  defaultValue) {
		super(key, defaultValue);
	}

	public Str2StrConverter(int id, String key, String defaultValue) {
		super(key, defaultValue);
	}

	/**
	 * 默认将替换所有空格
	 * 
	 */
	@Override
	public String convert(String value) throws ConstConvertFailureException {
		return StringUtils.trimWhitespace(value);
	}
}
