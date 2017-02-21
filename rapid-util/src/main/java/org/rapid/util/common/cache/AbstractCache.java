package org.rapid.util.common.cache;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractCache<ID, VALUE> implements Cache<ID, VALUE> {
	
	private static final Logger logger = LoggerFactory.getLogger(AbstractCache.class);
	
	private String name;
	protected Map<ID, VALUE> cache = new ConcurrentHashMap<ID, VALUE>();
	
	protected AbstractCache(String name) {
		this.name = name;
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
		// override if sub class need this method
		return null;
	}

	@Override
	public List<VALUE> getByProperties(Map<String, Object> params) {
		// override if sub class need this method
		return null;
	}
}
