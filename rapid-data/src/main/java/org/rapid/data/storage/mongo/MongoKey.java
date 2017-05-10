package org.rapid.data.storage.mongo;

import org.rapid.util.common.model.UniqueModel;

public class MongoKey implements UniqueModel<String> {

	private static final long serialVersionUID = 2287870705085446369L;
	
	private String name;
	private long value;
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public long getValue() {
		return value;
	}
	
	public void setValue(long value) {
		this.value = value;
	}
	
	@Override
	public String key() {
		return name;
	}
}
