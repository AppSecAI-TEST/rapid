package org.rapid.util.common.serializer;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.rapid.util.common.Consts;
import org.rapid.util.common.converter.StrConverter;
import org.rapid.util.common.converter.str.Str2BoolConverter;
import org.rapid.util.common.converter.str.Str2ByteConverter;
import org.rapid.util.common.converter.str.Str2DoubleConverter;
import org.rapid.util.common.converter.str.Str2FloatConverter;
import org.rapid.util.common.converter.str.Str2IntConverter;
import org.rapid.util.common.converter.str.Str2LongConverter;
import org.rapid.util.common.converter.str.Str2ShortConverter;
import org.rapid.util.common.converter.str.Str2StrConverter;
import org.rapid.util.common.key.Pair;

import com.google.gson.Gson;

import io.protostuff.LinkedBuffer;
import io.protostuff.ProtostuffIOUtil;
import io.protostuff.Schema;
import io.protostuff.runtime.RuntimeSchema;
import net.sf.cglib.beans.BeanMap;

@SuppressWarnings("unchecked")
public interface SerializeUtil {

	class JsonUtil {
		public static final Gson GSON = new Gson();
	}

	class XmlUtil {

		/**
		 * Please be attention, the clazz must annotated
		 * with @XmlRootElement(name="rootElementName")
		 * 
		 * @param xml
		 * @param clazz
		 * @return
		 * @throws JAXBException
		 */
		public static <T> T xmlToBean(String xml, Class<T> clazz) {
			try {
				JAXBContext jaxbContext = JAXBContext.newInstance(clazz);
				Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
				T instance = (T) unmarshaller.unmarshal(new StringReader(xml));
				return instance;
			} catch (JAXBException e) {
				throw new RuntimeException("Serial failure!", e);
			}
		}

		public static String beanToXml(Object obj, String encoding) {
			String strxml = null;
			try {
				JAXBContext context = JAXBContext.newInstance(obj.getClass());
				Marshaller marshaller = context.createMarshaller();
				marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
				marshaller.setProperty(Marshaller.JAXB_ENCODING, encoding);
				StringWriter writer = new StringWriter();
				marshaller.marshal(obj, writer);
				strxml = writer.toString();
			} catch (JAXBException e) {
				e.printStackTrace();
			}
			return strxml;
		}
	}

	class ProtostuffUtil {
		public static final <T> byte[] serial(T object) {
			Class<T> clazz = (Class<T>) object.getClass();
			Schema<T> schema = RuntimeSchema.getSchema(clazz);
			LinkedBuffer buffer = LinkedBuffer.allocate();
			try {
				return ProtostuffIOUtil.toByteArray(object, schema, buffer);
			} finally {
				buffer.clear();
			}
		}

		public static final <T> int serial(T object, OutputStream out) throws IOException {
			Class<T> clazz = (Class<T>) object.getClass();
			Schema<T> schema = RuntimeSchema.getSchema(clazz);
			LinkedBuffer buffer = LinkedBuffer.allocate();
			try {
				return ProtostuffIOUtil.writeTo(out, object, schema, buffer);
			} finally {
				buffer.clear();
			}
		}

		public static final <T> T deserial(byte[] data, Class<T> clazz) {
			Schema<T> schema = RuntimeSchema.getSchema(clazz);
			T t = schema.newMessage();
			ProtostuffIOUtil.mergeFrom(data, t, schema);
			return t;
		}

		public static final <T> T deserial(InputStream input, Class<T> clazz) throws IOException {
			Schema<T> schema = RuntimeSchema.getSchema(clazz);
			T t = schema.newMessage();
			ProtostuffIOUtil.mergeFrom(input, t, schema);
			return t;
		}
	}

	class RedisUtil {
		public static final byte[] encode(Object value) {
			return (value instanceof byte[]) ? (byte[]) value : _encode(value.toString());
		}

		public static final byte[][] encode(Object... params) {
			byte[][] buffer = new byte[params.length][];
			for (int i = 0, len = params.length; i < len; i++) {
				if (params[i] instanceof byte[])
					buffer[i] = (byte[]) params[i];
				else
					buffer[i] = _encode(params[i].toString());
			}
			return buffer;
		}

		private static final byte[] _encode(String value) {
			return value.getBytes(Consts.UTF_8);
		}

		public static final String decode(byte[] buffer) {
			return new String(buffer, Consts.UTF_8);
		}
	}

	class BeanMapUtil {
		private static Map<Pair<String, String>, StrConverter<?>> CACHE = new HashMap<Pair<String, String>, StrConverter<?>>();

		public static <T> Map<String, String> beanToMap(T bean) {
			Map<String, String> map = new HashMap<String, String>();
			BeanMap beanMap = BeanMap.create(bean);
			for (Object key : beanMap.keySet()) {
				Object value = beanMap.get(key);
				if (null == value)
					continue;
				map.put(key.toString(), value.toString());
			}
			return map;
		}

		public static <T> T mapToBean(Map<String, String> map, Class<T> clazz) {
			T bean = null;
			try {
				bean = clazz.newInstance();
			} catch (InstantiationException | IllegalAccessException e) {
				throw new RuntimeException("Class " + clazz.getName() + " constructor invoke failure!", e);
			}
			Map<String, Object> temp = _convert(map, bean);
			BeanMap beanMap = BeanMap.create(bean);
			beanMap.putAll(temp);
			return bean;
		}

		private static Map<String, Object> _convert(Map<String, String> map, Object bean) {
			Class<?> clazz = bean.getClass();
			String className = clazz.getName();
			Method[] methods = clazz.getMethods();

			Map<String, Object> temp = new HashMap<String, Object>();
			Iterator<Entry<String, String>> iterator = map.entrySet().iterator();
			while (iterator.hasNext()) {
				Entry<String, String> entry = iterator.next();
				String field = entry.getKey();
				Pair<String, String> pair = new Pair<String, String>(className, field);
				StrConverter<?> converter = CACHE.get(pair);
				if (null == converter) {
					for (Method method : methods) {
						String methodName = method.getName();
						if (!methodName.startsWith("set")
								|| !method.getName().toUpperCase().equals("SET" + field.toUpperCase()))
							continue;
						Class<?> c = method.getParameterTypes()[0];
						if (c == int.class || c == Integer.class) {
							converter = Str2IntConverter.INSTANCE;
							CACHE.put(pair, converter);
							break;
						} else if (c == float.class || c == Float.class) {
							converter = Str2FloatConverter.INSTANCE;
							CACHE.put(pair, converter);
							break;
						} else if (c == long.class || c == Long.class) {
							converter = Str2LongConverter.INSTANCE;
							CACHE.put(pair, converter);
							break;
						} else if (c == double.class || c == Double.class) {
							converter = Str2DoubleConverter.INSTANCE;
							CACHE.put(pair, converter);
							break;
						} else if (c == short.class || c == Short.class) {
							converter = Str2ShortConverter.INSTANCE;
							CACHE.put(pair, converter);
							break;
						} else if (c == byte.class || c == Byte.class) {
							converter = Str2ByteConverter.INSTANCE;
							CACHE.put(pair, converter);
							break;
						} else if (c == boolean.class || c == Boolean.class) {
							converter = Str2BoolConverter.INSTANCE;
							CACHE.put(pair, converter);
							break;
						} else {
							converter = Str2StrConverter.INSTANCE;
							CACHE.put(pair, converter);
							break;
						}
					}
				}
				if (null != converter)
					temp.put(field, converter.convert(entry.getValue()));
			}
			return temp;
		}
	}
}
