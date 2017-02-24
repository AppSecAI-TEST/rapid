package org.rapid.util.common.key;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 单独一个对象作为  key 
 * 
 * @author ahab
 *
 * @param <KEY>
 */
public class Key<KEY> {
	
	private static final Map<Key<?>, Boolean> keys = new ConcurrentHashMap<Key<?>, Boolean>();
	
	public static final Key<Integer> JSON_ENTITY_SERIALIZER					= instance(1);
	public static final Key<Integer> PROTOSTUFF_ENTITY_SERIALIZER			= instance(2);

	private KEY key;
	
	private Key(KEY key) {
		this.key = key;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((key == null) ? 0 : key.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Key<?> other = (Key<?>) obj;
		if (key == null) {
			if (other.key != null)
				return false;
		} else if (!key.equals(other.key))
			return false;
		return true;
	}
	
	public KEY key() {
		return key;
	}
	
	@Override
	public String toString() {
		return key.toString();
	}
	
	public static final <KEY> Key<KEY> instance(KEY key) {
		Key<KEY> temp =  new Key<KEY>(key);
		if (null != keys.putIfAbsent(temp, Boolean.TRUE))
			throw new RuntimeException("Key conflict - " + temp);
		return temp;
	}
}
