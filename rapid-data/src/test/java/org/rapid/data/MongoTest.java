package org.rapid.data;


import org.bson.Document;
import org.rapid.data.storage.mongo.KeyMapper;
import org.rapid.data.storage.mongo.Mongo;

import com.mongodb.client.model.Filters;
import com.mongodb.client.model.FindOneAndUpdateOptions;
import com.mongodb.client.model.UpdateOptions;
import com.mongodb.client.model.Updates;

import junit.framework.TestCase;

public class MongoTest extends TestCase {
	
	protected Mongo mongo;
	protected KeyMapper keyMapper;

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		mongo = new Mongo();
		mongo.setDb("btkj-test");
		mongo.setHost("101.37.30.26");
		mongo.init();
		keyMapper = new KeyMapper();
		keyMapper.setMongo(mongo);
	}
	
	public void testReplaceOne() {
		mongo.replaceOne("findOneAndUpdate", Filters.eq("_id", "2"), new Document().append("sss", "hessllo"), new UpdateOptions().upsert(true));
	}
	
	public void testFindOneAndUpdate() {
		mongo.findOneAndUpdate("findOneAndUpdate", 
				Filters.eq("_id", "1"), Updates.set("policies.1", new Document().append("world", "hello")), 
				new FindOneAndUpdateOptions().upsert(true));
	}
}
