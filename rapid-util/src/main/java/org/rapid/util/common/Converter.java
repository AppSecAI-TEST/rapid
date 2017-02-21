package org.rapid.util.common;

import org.rapid.util.exception.ConvertFailuerException;

/**
 * 将 S 转换成 T 的转换类
 * 
 * @author ahab
 *
 * @param <T>
 * @param <S>
 */
public interface Converter<S, T> {

	T convert(S k) throws ConvertFailuerException;
}
