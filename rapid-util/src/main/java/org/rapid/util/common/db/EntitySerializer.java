package org.rapid.util.common.db;

import org.rapid.util.common.Serializer;

public interface EntitySerializer<KEY, DATA extends Entity<KEY>, T> extends Serializer<DATA, T> {
	
}
