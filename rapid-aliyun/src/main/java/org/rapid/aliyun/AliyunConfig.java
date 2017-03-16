package org.rapid.aliyun;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.rapid.util.common.consts.conveter.StrConstConverter;
import org.rapid.util.exception.ConstConvertFailureException;
import org.rapid.util.io.ResourceUtil;
import org.rapid.util.lang.StringUtils;

public class AliyunConfig {
	
	private Properties props;

	/**
	 * 阿里云的配置文件路径
	 * 
	 * @param resources
	 * @throws IOException 
	 */
	public AliyunConfig(String resource) throws IOException {
		_load(resource);
	}
	
	private void _load(String resource) throws IOException { 
		File file = ResourceUtil.getFile(resource);
		if (null == file || !file.isFile())
			throw new IOException("File type error!");
		props = new Properties();
		props.load(new FileInputStream(file));
	}
	
	/**
	 * <pre>
	 * 获取 key - value 形式的键值对，URL 之后的参数，以及 x-www-form-urlencoded 编码的参数可以使用此种方式来获取
	 * null 和 "" 默认都是不合法的
	 * </pre>
	 * 
	 * @param constant
	 * @return
	 * @throws IllegalConstException
	 */
	public <T> T getConfig(StrConstConverter<T> constant) throws ConstConvertFailureException {
		String val = props.getProperty(constant.key());
		if (!StringUtils.hasText(val))
			throw ConstConvertFailureException.nullConstException(constant);
		try {
			return constant.convert(val);
		} catch (Exception e) {
			throw new ConstConvertFailureException(constant, e);
		}
	}
	
	/**
	 * 和 {@link #getConfig(StrConstConverter)} 的不同之处在于如果参数不存在或者错误，不会抛出异常，而是返回 {@link StrConstConverter} 的默认值
	 * 
	 * @param constant
	 * @return
	 */
	public <T> T getOptionalConfig(StrConstConverter<T> constant) {
		try {
			return getConfig(constant);
		} catch (ConstConvertFailureException e) {
			return constant.value();
		}
	}
}
