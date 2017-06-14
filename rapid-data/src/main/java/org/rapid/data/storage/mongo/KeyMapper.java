package org.rapid.data.storage.mongo;

import org.bson.Document;
import org.rapid.data.storage.mapper.MongoMapper;

import com.mongodb.client.model.Filters;
import com.mongodb.client.model.FindOneAndUpdateOptions;
import com.mongodb.client.model.Projections;
import com.mongodb.client.model.ReturnDocument;
import com.mongodb.client.model.Updates;

public class KeyMapper extends MongoMapper<String, MongoKey> {
	
	private static final String NAME			= "name";
	private static final String VALUE			= "value";

	public KeyMapper() {
		super("keys");
	}
	
	public long getAndInc(String key, Number number) { 
		Document document = mongo.findOneAndUpdate(collection, Filters.eq(NAME, key), Updates.inc(VALUE, number), 
				new FindOneAndUpdateOptions().upsert(true).returnDocument(ReturnDocument.AFTER).projection(Projections.excludeId()));
		return deserial(document).getValue();
	}
}
