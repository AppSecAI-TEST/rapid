package org.rapid.data.storage.mongo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.rapid.util.common.model.UniqueModel;
import org.rapid.util.common.serializer.SerializeUtil;

import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.FindOneAndUpdateOptions;
import com.mongodb.client.model.ReplaceOneModel;
import com.mongodb.client.model.UpdateOneModel;
import com.mongodb.client.model.UpdateOptions;

public class Mongo {
	
	private String db;
	private String host;
	private MongoClient mongo;
	private MongoDatabase connection;
	
	public void init() {
		this.mongo = new MongoClient(host);
		this.connection = mongo.getDatabase(db);
	}
	
	public void insertOne(String collectionName, Object object) {
		MongoCollection<Document> collection = connection.getCollection(collectionName);
		collection.insertOne(serial(object));
	}
	
	public void insertMany(String collectionName, List<?> objects) {
		MongoCollection<Document> collection = connection.getCollection(collectionName);
		List<Document> list = new ArrayList<Document>(objects.size());
		for (Object object : objects)
			list.add(serial(object));
		collection.insertMany(list);
	}
	
	public <T> List<T> find(String collectionName, Class<T> clazz) { 
		MongoCollection<Document> collection = connection.getCollection(collectionName);
		FindIterable<Document> iterable = collection.find();
		List<T> list = new ArrayList<T>();
		MongoCursor<Document> cursor = iterable.iterator();
		while (cursor.hasNext()) 
			list.add(SerializeUtil.JsonUtil.GSON.fromJson(cursor.next().toJson(), clazz));
		return list;
	}
	
	public <KEY, T extends UniqueModel<KEY>> Map<KEY, T> findMap(String collectionName, Bson filter, Class<T> clazz) { 
		MongoCollection<Document> collection = connection.getCollection(collectionName);
		FindIterable<Document> iterable = collection.find(filter);
		Map<KEY, T> map = new HashMap<KEY, T>();
		MongoCursor<Document> cursor = iterable.iterator();
		while (cursor.hasNext()) {
			T t = SerializeUtil.JsonUtil.GSON.fromJson(cursor.next().toJson(), clazz);
			map.put(t.key(), t);
		}
		return map;
	}
	
	public <T> List<T> find(String collectionName, Bson filter, Class<T> clazz) { 
		MongoCollection<Document> collection = connection.getCollection(collectionName);
		FindIterable<Document> iterable = collection.find(filter);
		List<T> list = new ArrayList<T>();
		MongoCursor<Document> cursor = iterable.iterator();
		while (cursor.hasNext()) 
			list.add(SerializeUtil.JsonUtil.GSON.fromJson(cursor.next().toJson(), clazz));
		return list;
	}
	
	public void bulkUpdateOne(String collectionName, Map<Bson, Bson> updates) {
		MongoCollection<Document> collection = connection.getCollection(collectionName);
		List<UpdateOneModel<Document>> list = new ArrayList<UpdateOneModel<Document>>(updates.size());
		for (Entry<Bson, Bson> entry : updates.entrySet())
			list.add(new UpdateOneModel<Document>(entry.getKey(), entry.getValue()));
		collection.bulkWrite(list);
	}
	
	public <KEY, MODEL extends UniqueModel<KEY>> void bulkReplaceOne(String collectionName, Map<KEY, MODEL> replaces) {
		MongoCollection<Document> collection = connection.getCollection(collectionName);
		List<ReplaceOneModel<Document>> list = new ArrayList<ReplaceOneModel<Document>>(replaces.size());
		for (MODEL model : replaces.values()) 
			list.add(new ReplaceOneModel<Document>(Filters.eq("_id", model.key()), serial(model), new UpdateOptions().upsert(true)));
		collection.bulkWrite(list);
	}
	
	/**
	 * 分页显示：排序
	 * 
	 * @param collectionName
	 * @param filter
	 * @param sort
	 * @param clazz
	 * @param start
	 * @param pageSize
	 * @return
	 */
	public <T> List<T> pagingAndSort(String collectionName, Bson filter, Bson sort, int start, int pageSize, Class<T> clazz) {
		MongoCollection<Document> collection = connection.getCollection(collectionName);
		FindIterable<Document> iterable = collection.find(filter).sort(sort).skip(start).limit(pageSize);
		List<T> list = new ArrayList<T>(0);
		MongoCursor<Document> cursor = iterable.iterator();
		while (cursor.hasNext()) 
			list.add(SerializeUtil.JsonUtil.GSON.fromJson(cursor.next().toJson(), clazz));
		return list;
	}
	
	public <T> List<T> pagingAndSort(String collectionName, Bson sort, int start, int pageSize, Class<T> clazz) {
		MongoCollection<Document> collection = connection.getCollection(collectionName);
		FindIterable<Document> iterable = collection.find().sort(sort).skip(start).limit(pageSize);
		List<T> list = new ArrayList<T>(0);
		MongoCursor<Document> cursor = iterable.iterator();
		while (cursor.hasNext()) 
			list.add(SerializeUtil.JsonUtil.GSON.fromJson(cursor.next().toJson(), clazz));
		return list;
	}
	
	/**
	 * 分页显示：不排序
	 * 
	 * @param collectionName
	 * @param filter
	 * @param start
	 * @param pageSize
	 * @param clazz
	 * @return
	 */
	public <T> List<T> paging(String collectionName, Bson filter, int start, int pageSize, Class<T> clazz) {
		MongoCollection<Document> collection = connection.getCollection(collectionName);
		FindIterable<Document> iterable = collection.find(filter).skip(start).limit(pageSize);
		List<T> list = new ArrayList<T>(0);
		MongoCursor<Document> cursor = iterable.iterator();
		while (cursor.hasNext()) 
			list.add(SerializeUtil.JsonUtil.GSON.fromJson(cursor.next().toJson(), clazz));
		return list;
	}
	
	public <T> List<T> paging(String collectionName, int start, int pageSize, Class<T> clazz) {
		MongoCollection<Document> collection = connection.getCollection(collectionName);
		FindIterable<Document> iterable = collection.find().skip(start).limit(pageSize);
		List<T> list = new ArrayList<T>(0);
		MongoCursor<Document> cursor = iterable.iterator();
		while (cursor.hasNext()) 
			list.add(SerializeUtil.JsonUtil.GSON.fromJson(cursor.next().toJson(), clazz));
		return list;
	}
	
	public long count(String collectionName, Bson filter) {
		MongoCollection<Document> collection = connection.getCollection(collectionName);
		return collection.count(filter);
	}
	
	public long count(String collectionName) {
		MongoCollection<Document> collection = connection.getCollection(collectionName);
		return collection.count();
	}
	
	public <T> T findOne(String collectionName, Bson filter, Class<T> clazz) { 
		MongoCollection<Document> collection = connection.getCollection(collectionName);
		FindIterable<Document> iterable = collection.find(filter);
		Document document = iterable.first();
		return null == document ? null : deserial(document, clazz);
	}
	
	public <T> T findOne(String collectionName, Bson filter, Bson sort, Class<T> clazz) { 
		MongoCollection<Document> collection = connection.getCollection(collectionName);
		FindIterable<Document> iterable = collection.find(filter).sort(sort);
		Document document = iterable.first();
		return null == document ? null : deserial(document, clazz);
	}
	
	public void replaceOne(String collectionName, Bson filter, Object replacement) { 
		MongoCollection<Document> collection = connection.getCollection(collectionName);
		collection.replaceOne(filter, serial(replacement));
	}
	
	public void replaceOne(String collectionName, Bson filter, Object replacement, UpdateOptions options) {
		MongoCollection<Document> collection = connection.getCollection(collectionName);
		collection.replaceOne(filter, serial(replacement), options);
	}
	
	public <T> T findOneAndUpdate(String collectionName, Bson filter, Bson update, FindOneAndUpdateOptions options, Class<T> clazz) { 
		MongoCollection<Document> collection = connection.getCollection(collectionName);
		Document document = collection.findOneAndUpdate(filter, update, options);
		return null == document ? null : deserial(document, clazz);
	}
	
	public void update(String collectionName, Bson filter, Bson update) {
		MongoCollection<Document> collection = connection.getCollection(collectionName);
		collection.updateMany(filter, update);
	}
	
	public void deleteMany(String collectionName, Bson filter) {
		MongoCollection<Document> collection = connection.getCollection(collectionName);
		collection.deleteMany(filter);
	}
	
	public void deleteOne(String collectionName, Bson filter) {
		MongoCollection<Document> collection = connection.getCollection(collectionName);
		collection.deleteOne(filter);
	}
	
	public Document serial(Object model) {
		return Document.parse(SerializeUtil.JsonUtil.GSON.toJson(model));
	}
	
	public <T> T deserial(Document document, Class<T> clazz) {
		return SerializeUtil.JsonUtil.GSON.fromJson(document.toJson(), clazz);
	}
	
	public MongoCollection<Document> collection(String collectionName) {
		return connection.getCollection(collectionName);
	}
	
	public void setDb(String db) {
		this.db = db;
	}
	
	public void setHost(String host) {
		this.host = host;
	}
}
