package org.rapid.data.storage.mapper;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

import org.bson.Document;
import org.rapid.data.storage.mongo.Mongo;
import org.rapid.util.common.model.UniqueModel;
import org.rapid.util.common.serializer.SerializeUtil;

/**
 * MongoDB 的 映射类
 * 
 * @author ahab
 *
 * @param <DATA>
 * @param <KEY>
 * @param <MODEL>
 */
public class MongoMapper<KEY, MODEL extends UniqueModel<KEY>> implements IMapper<KEY, MODEL> {
	
	protected String FIELD_ID					= "_id";

	protected Mongo mongo;
	protected String collection;
	protected Class<MODEL> clazz;
	
	@SuppressWarnings("unchecked")
	public MongoMapper(String collection) {
		this.collection = collection;
		Type superType = getClass().getGenericSuperclass();   
		Type[] generics = ((ParameterizedType) superType).getActualTypeArguments();  
		clazz = (Class<MODEL>) generics[1];
	}
	
	public void setMongo(Mongo mongo) {
		this.mongo = mongo;
	}

	@Override
	public MODEL insert(MODEL model) {
		mongo.insertOne(collection, serial(model));
		return model;
	}

	@Override
	public MODEL getByKey(KEY key) {
		return null;
	}

	@Override
	public List<MODEL> getWithinKey(List<KEY> keys) {
		return null;
	}

	@Override
	public void update(MODEL model) {
	}
	
	protected Document serial(MODEL model) {
		return Document.parse(SerializeUtil.JsonUtil.GSON.toJson(model));
	}
	
	protected MODEL deserial(Document document) {
		return SerializeUtil.JsonUtil.GSON.fromJson(document.toJson(), clazz);
	}
}
