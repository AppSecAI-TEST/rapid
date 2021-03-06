package org.rapid.util.common.serializer;

import org.rapid.util.common.converter.Converter;
import org.rapid.util.exception.ConvertFailuerException;

/**
 * 序列化类不仅能够正向转换(序列化)，还可以将数据反向转换(反序列化)
 * 
 * @author ahab
 *
 * @param <S>
 * @param <T>
 */
public interface Serializer<S, T> extends Converter<S, T> {

	/**
	 * 将 T 反转为 S
	 * 
	 * @param t
	 * @return
	 * @throws ConvertFailuerException
	 */
	S antiConvet(T t) throws ConvertFailuerException;
	
	/**
	 * 反序列化需要设置需要 java 的 class 类型
	 * 
	 * @param clazz
	 */
	void setClazz(Class<S> clazz);
}
