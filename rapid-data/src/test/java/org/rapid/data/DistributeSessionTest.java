package org.rapid.data;

import java.util.concurrent.TimeUnit;

import org.rapid.data.storage.redis.DistributeSession;

public class DistributeSessionTest extends RedisTest {
	
	private DistributeSession session;

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		
		session = new DistributeSession(redis);
	}
	
	public void testLocal() throws InterruptedException { 
		String value = session.put("test", "hello");
		System.out.println(value);
		TimeUnit.SECONDS.sleep(10);
		value = session.get("test");
		System.out.println(value);
		TimeUnit.SECONDS.sleep(10);
		value = session.getAndDel("test");
		System.out.println(value);
	}
}
