package org.rapid.data.storage.mongo;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.rapid.util.common.serializer.SerializeUtil;

import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.FindOneAndUpdateOptions;
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
	
	public void insertOne(String collectionName, Document document) {
		MongoCollection<Document> collection = connection.getCollection(collectionName);
		collection.insertOne(document);
	}
	
	public <T> List<T> find(String collectionName, Class<T> clazz) { 
		MongoCollection<Document> collection = connection.getCollection(collectionName);
		FindIterable<Document> iterable = collection.find();
		List<T> list = new ArrayList<T>(4);
		MongoCursor<Document> cursor = iterable.iterator();
		while (cursor.hasNext()) 
			list.add(SerializeUtil.JsonUtil.GSON.fromJson(cursor.next().toJson(), clazz));
		return list;
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
	
	public Document findOne(String collectionName, Bson filter) { 
		MongoCollection<Document> collection = connection.getCollection(collectionName);
		FindIterable<Document> iterable = collection.find(filter);
		return iterable.first();
	}
	
	public void replaceOne(String collectionName, Bson filter, Document replacement) { 
		MongoCollection<Document> collection = connection.getCollection(collectionName);
		collection.replaceOne(filter, replacement);
	}
	
	public void replaceOne(String collectionName, Bson filter, Document replacement, UpdateOptions options) { 
		MongoCollection<Document> collection = connection.getCollection(collectionName);
		collection.replaceOne(filter, replacement, options);
	}
	
	public Document findOneAndUpdate(String collectionName, Bson filter, Bson update) { 
		MongoCollection<Document> collection = connection.getCollection(collectionName);
		return collection.findOneAndUpdate(filter, update);
	}

	public Document findOneAndUpdate(String collectionName, Bson filter, Bson update, FindOneAndUpdateOptions options) { 
		MongoCollection<Document> collection = connection.getCollection(collectionName);
		return collection.findOneAndUpdate(filter, update, options);
	}
	
	public void setDb(String db) {
		this.db = db;
	}
	
	public void setHost(String host) {
		this.host = host;
	}
}
