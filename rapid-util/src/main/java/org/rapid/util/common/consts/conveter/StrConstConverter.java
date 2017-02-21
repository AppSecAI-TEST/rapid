package org.rapid.util.common.consts.conveter;

import org.rapid.util.common.Converter;
import org.rapid.util.common.consts.Const;
import org.rapid.util.exception.ConstConvertFailureException;

/**
 * string 类型转换器，能将 string 类型转换成其他类型，value() 值表示默认值
 * 
 * @author ahab
 *
 * @param <T>
 */
public interface StrConstConverter<T> extends Const<T>, Converter<String, T> {

	/**
	 * 将 string 类型的数据转换成其他类型
	 * 
	 * @param value
	 * @return
	 */
	@Override
	T convert(String k) throws ConstConvertFailureException;
}
