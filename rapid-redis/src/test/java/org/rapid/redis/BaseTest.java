package org.rapid.redis;

import junit.framework.TestCase;
import redis.clients.jedis.JedisPool;

public class BaseTest extends TestCase {

	protected JedisPool pool;
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		
		pool = new JedisPool("www.baotukj.com", 6000);
	}
}
