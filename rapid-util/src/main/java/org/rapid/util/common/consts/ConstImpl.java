package org.rapid.util.common.consts;

import java.util.HashSet;
import java.util.Set;

public class ConstImpl<T> implements Const<T> {
	
	private static final Set<Integer> ID_HOLDER = new HashSet<Integer>();
	
	private int id;
	private String key;
	private T value;
	
	public ConstImpl(String key, T value) {
		this(0, key, value);
	}
	
	public ConstImpl(int id, String key, T value) {
		if (0 != id && !ID_HOLDER.add(id)) 
			throw new RuntimeException("Duplicated const id for : " + id);
		this.id = id;
		this.key = key;
		this.value = value;
	}
	
	@Override
	public int id() {
		return id;
	}

	@Override
	public String key() {
		return this.key;
	}
	
	@Override
	public T value() {
		return value;
	}
}
