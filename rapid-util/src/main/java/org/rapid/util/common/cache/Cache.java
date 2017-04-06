package org.rapid.util.common.cache;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class Cache<ID, VALUE> implements ICache<ID, VALUE> {
	
	private static final Logger logger = LoggerFactory.getLogger(Cache.class);
	
	private String name;
//	private Map<String, Method> getter;
	protected Map<ID, VALUE> cache = new ConcurrentHashMap<ID, VALUE>();
	
	protected Cache(String name) {
		this.name = name;
//		this.getter = new HashMap<String, Method>();
//		Type superType = getClass().getGenericSuperclass();   
//		Type[] generics = ((ParameterizedType) superType).getActualTypeArguments();   
//		Class<VALUE> clazz = (Class<VALUE>) generics[1];
//		PropertyDescriptor[] descriptors = ReflectUtils.getBeanGetters(clazz);
//		for (PropertyDescriptor descriptor : descriptors)
//			getter.put(descriptor.getName(), descriptor.getReadMethod());
	}

	@Override
	public String name() {
		return this.name;
	}
	
	@Override
	public void reload() throws Exception {
		Map<ID, VALUE> temp = new HashMap<ID, VALUE>(this.cache);
		try {
			load();
			temp.clear();
			temp = null;
		} catch (Exception e) {
			this.cache.clear();
			this.cache = temp;
			logger.warn("Cache {} reload failure!", name, e);
		}
	}

	@Override
	public void dispose() {
		this.cache.clear();
		logger.info("Cache {} dispose!", name);
	}

	@Override
	public VALUE getById(ID id) {
		return cache.get(id);
	}

	@Override
	public List<VALUE> getAll() {
		return new ArrayList<VALUE>(cache.values());
	}

	@Override
	public List<VALUE> getByProperties(String property, Object value) {
		return null;
	}

	@Override
	public List<VALUE> getByProperties(Map<String, Object> params) {
		return null;
	}
	
}
