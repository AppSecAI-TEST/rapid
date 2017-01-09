package org.rapid.soa.common.serialize.kryo;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.rapid.soa.common.model.User;

import com.alibaba.dubbo.common.serialize.support.SerializationOptimizer;

@SuppressWarnings("rawtypes")
public class SerializationOptimizerImpl implements SerializationOptimizer {

	@Override
	public Collection<Class> getSerializableClasses() {
		List<Class> classes = new LinkedList<Class>();
        classes.add(User.class);
        return classes;
	}
}
