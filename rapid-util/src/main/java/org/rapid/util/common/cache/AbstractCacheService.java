package org.rapid.util.common.cache;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SuppressWarnings("unchecked")
public abstract class AbstractCacheService<CACHE extends Cache<?, ?>> implements CacheService<CACHE> {
	
	private static final Logger logger = LoggerFactory.getLogger(AbstractCacheService.class);
	
	protected Map<String, CACHE> caches = new ConcurrentHashMap<String, CACHE>();

	@Override
	public void dispose() {
		this.caches.clear();
		logger.info("{} cache were disposed from CacheService!", caches.size());
	}

	@Override
	public <ID, VALUE> Cache<ID, VALUE> getCache(String name) {
		return (Cache<ID, VALUE>) caches.get(name);
	}

	@Override
	public <ID, VALUE> VALUE getById(String name, ID id) {
		Cache<ID, VALUE> cache = getCache(name);
		return cache.getById(id);
	}

	@Override
	public <ID, VALUE> List<VALUE> getAll(String name) {
		Cache<ID, VALUE> cache = getCache(name);
		return cache.getAll();
	}

	@Override
	public <ID, VALUE> List<VALUE> getByProperties(String name, String property, Object value) {
		Cache<ID, VALUE> cache = getCache(name);
		return cache.getByProperties(property, value);
	}

	@Override
	public <ID, VALUE> List<VALUE> getByProperties(String name, Map<String, Object> params) {
		Cache<ID, VALUE> cache = getCache(name);
		return cache.getByProperties(params);
	}

}
