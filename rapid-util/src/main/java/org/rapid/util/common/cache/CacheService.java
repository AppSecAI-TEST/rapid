package org.rapid.util.common.cache;

import java.util.List;
import java.util.Map;

public interface CacheService<CACHE extends Cache<?, ?>> {

	void init();
	
	void dispose();
	
	CACHE getCache(String name);
	
	<ID, VALUE> VALUE getById(String name, ID id); 
	
	<ID, VALUE> List<VALUE> getAll(String name); 
	
	<ID, VALUE> List<VALUE> getByProperties(String name, String property, Object value); 
	
	<ID, VALUE> List<VALUE> getByProperties(String name, Map<String, Object> params);
}
