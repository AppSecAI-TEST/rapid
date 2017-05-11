package org.rapid.data.storage.mongo;

import org.bson.Document;
import org.bson.conversions.Bson;

import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.FindOneAndUpdateOptions;

public class Mongo {
	
	private String db;
	private String host;
	private MongoClient mongo;
	private MongoDatabase connection;
	
	@SuppressWarnings("unused")
	private void init() {
		this.mongo = new MongoClient(host);
		this.connection = mongo.getDatabase(db);
	}
	
	public void insertOne(String collectionName, Document document) {
		MongoCollection<Document> collection = connection.getCollection(collectionName);
		collection.insertOne(document);
	}
	
	public Document findOne(String collectionName, Bson filter) { 
		MongoCollection<Document> collection = connection.getCollection(collectionName);
		FindIterable<Document> iterable = collection.find(filter);
		return iterable.first();
	}
	
	public void replace(String collectionName, Bson filter, Document document) { 
		MongoCollection<Document> collection = connection.getCollection(collectionName);
		collection.replaceOne(filter, document);
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
