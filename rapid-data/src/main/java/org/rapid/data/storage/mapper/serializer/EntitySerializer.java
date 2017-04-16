package org.rapid.data.storage.mapper.serializer;

import org.rapid.util.common.model.UniqueModel;
import org.rapid.util.common.serializer.Serializer;

public interface EntitySerializer<KEY, ENTITY extends UniqueModel<KEY>, T> extends Serializer<ENTITY, T> {
	
}
