package org.rapid.redis;

import java.util.HashSet;
import java.util.Set;

import junit.framework.TestCase;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisSentinelPool;

public class BaseTest extends TestCase {

	protected JedisSentinelPool pool;
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		
		Set<String> sentinels = new HashSet<String>();
		sentinels.add("101.37.30.26:26379");
		sentinels.add("101.37.30.26:26380");
		sentinels.add("101.37.30.26:26381");
		pool = new JedisSentinelPool("btkj-test", sentinels, new JedisPoolConfig(), 3000, "hzbtkj001");
	}
}
