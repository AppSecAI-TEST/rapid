package org.rapid.data.storage.mapper;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

import javax.annotation.Resource;

import org.bson.Document;
import org.rapid.data.storage.mongo.Mongo;
import org.rapid.util.common.model.UniqueModel;
import org.rapid.util.common.serializer.SerializeUtil;

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
	
	public void setMongo(Mongo mongo) {
		this.mongo = mongo;
	}

	@Override
	public void insert(MODEL model) {
		mongo.insertOne(collection, serial(model));
	}

	@Override
	public MODEL getByKey(KEY key) {
		Document document = mongo.findOne(collection, Filters.eq(FIELD_ID, key));
		return null == document ? null : deserial(document);
	}

	@Override
	public List<MODEL> getWithinKey(List<KEY> keys) {
		return null;
	}
	
	@Override
	public List<MODEL> getAll() {
		return null;
	}
	
	@Override
	public void update(MODEL model) {
		mongo.replaceOne(collection, Filters.eq(FIELD_ID, model.key()), serial(model));
	}
	
	@Override
	public void delete(KEY key) {
		mongo.deleteOne(collection, Filters.eq(FIELD_ID, key));
	}
	
	@Override
	public void delete(MODEL model) {
		mongo.deleteOne(collection, Filters.eq(FIELD_ID, model.key()));
	}

	protected Document serial(MODEL model) {
		return Document.parse(SerializeUtil.JsonUtil.GSON.toJson(model));
	}
	
	protected MODEL deserial(Document document) {
		return SerializeUtil.JsonUtil.GSON.fromJson(document.toJson(), clazz);
	}
}
