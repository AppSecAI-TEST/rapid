package org.rapid.util.common.db;

import org.rapid.util.common.Serializer;
import org.rapid.util.common.key.Key;

public interface EntitySerializer<KEY, DATA extends Entity<KEY>, T> extends Serializer<DATA, T> {
	
	Key<Integer> identity();
}
