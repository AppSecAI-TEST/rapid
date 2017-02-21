package org.rapid.redis;

import org.rapid.redis.RedisOption.EXPX;
import org.rapid.redis.RedisOption.NXXX;

public class RedisTest extends BaseTest {

	private Redis redis;
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		
		redis = new Redis();
		redis.setJedisPool(pool);
	}
	
	public void testInit() {
		long flag = redis.captchaObtain("captcha", "count", "1234", 10000, 2, 30000);
		System.out.println(flag);
		System.out.println(redis.get("captcha"));
		System.out.println(redis.get("count"));
		System.out.println(redis.ttl("captcha"));
		System.out.println(redis.ttl("count"));
	}

	public void testSet() {
		String result = redis.set("set", "123", NXXX.NX, EXPX.EX, 10);
		assertEquals(result, "OK");
		result = redis.set("set", "1234", NXXX.XX, EXPX.EX, 10);
		assertEquals(result, "OK");
		result = redis.set("set", "123", NXXX.NX, EXPX.EX, 10);
		assertNull(result);
	}
	
	public void testDelIfEquals() {
		String result = redis.set("captcha", "1234");
		assertEquals(result, "OK");
		assertFalse(redis.delIfEquals("captcha", "123"));
		assertTrue(redis.delIfEquals("captcha", "1234"));
	}
	
	public void testLock() { 
		redis.del("lock");
		String lockId = redis.tryLock("lock", 10000000);
		assertNotNull(lockId);
		lockId = redis.tryLock("lock", 10000000);
		assertNull(lockId);
		lockId = redis.lock("lock", 3000, 10000000);
		assertNull(lockId);
		redis.del("lock");
		lockId = redis.lock("lock", 3000, 10000000);
		assertNotNull(lockId);
	}
}
