package org.rapid.data.storage.mapper.serializer;

import org.rapid.data.storage.db.Entity;
import org.rapid.util.common.Serializer;

public interface EntitySerializer<KEY, ENTITY extends Entity<KEY>, T> extends Serializer<ENTITY, T> {
	
}
