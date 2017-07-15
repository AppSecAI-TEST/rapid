package org.rapid.data.storage.mapper;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.rapid.data.storage.mongo.Mongo;
import org.rapid.data.storage.mongo.MongoUtil;
import org.rapid.util.common.model.UniqueModel;
import org.rapid.util.lang.CollectionUtil;

import com.mongodb.client.model.Filters;

/**
 * MongoDB 的 映射类
 * 
 * @author ahab
 *
 * @param <DATA>
 * @param <KEY>
 * @param <MODEL>
 */
public class MongoMapper<KEY, MODEL extends UniqueModel<KEY>> implements Mapper<KEY, MODEL> {
	
	protected String FIELD_ID					= "_id";

	@Resource
	protected Mongo mongo;
	protected String collection;
	protected Class<MODEL> clazz;
	
	public MongoMapper(String collection) {
		this.collection = collection;
		Type superType = getClass().getGenericSuperclass();   
		Type[] generics = ((ParameterizedType) superType).getActualTypeArguments();  
		clazz = (Class<MODEL>) generics[1];
	}
	
	@Override
	public void insert(MODEL model) {
		mongo.insertOne(collection, model);
	}
	
	@Override
	public Map<KEY, MODEL> getAll() {
		return convertToMap(mongo.find(collection, clazz));
	}

	@Override
	public MODEL getByKey(KEY key) {
		return mongo.findOne(collection, Filters.eq(FIELD_ID, key), clazz);
	}

	@Override
	public Map<KEY, MODEL> getByKeys(Collection<KEY> keys) {
		Map<KEY, MODEL> map = new HashMap<KEY, MODEL>();
		if (!CollectionUtil.isEmpty(keys)) 
			loadInToMap(map, mongo.find(collection, MongoUtil.or(FIELD_ID, keys), clazz));
		return map;
	}
	
	@Override
	public void update(MODEL model) {
		mongo.replaceOne(collection, Filters.eq(FIELD_ID, model.key()), model);
	}
	
	@Override
	public void delete(KEY key) {
		mongo.deleteOne(collection, Filters.eq(FIELD_ID, key));
	}
	
	public void setMongo(Mongo mongo) {
		this.mongo = mongo;
	}
}
