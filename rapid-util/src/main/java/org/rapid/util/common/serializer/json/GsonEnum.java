package org.rapid.util.common.serializer.json;

public interface GsonEnum<E> {

	String serialize();
	
	E deserialize(String jsonEnum);
}
