package org.rapid.util.common.consts.conveter;

import org.rapid.util.common.consts.StrConst;
import org.rapid.util.exception.ConstConvertFailureException;
import org.rapid.util.lang.StringUtil;

public class Str2StrConstConverter extends StrConst implements StrConstConverter<String> {

	public Str2StrConstConverter(String key) {
		super(key, null);
	}
	
	public Str2StrConstConverter(int id, String key) {
		super(id, key, null);
	}

	public Str2StrConstConverter(String key, String  defaultValue) {
		super(key, defaultValue);
	}

	public Str2StrConstConverter(int id, String key, String defaultValue) {
		super(id, key, defaultValue);
	}

	/**
	 * 默认将替换所有空格
	 * 
	 */
	@Override
	public String convert(String value) throws ConstConvertFailureException {
		return StringUtil.trimWhitespace(value);
	}
}
