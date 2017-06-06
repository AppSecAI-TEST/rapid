package org.rapid.data;

import java.util.List;

import org.rapid.data.storage.redis.Redis;
import org.rapid.data.storage.redis.RedisOption.EXPX;
import org.rapid.data.storage.redis.RedisOption.NXXX;
import org.rapid.util.common.serializer.SerializeUtil;

@SuppressWarnings("all")
public class RedisTest extends BaseTest {

	protected Redis redis;
	
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
	
	public void testHgetAllAndPexpire() { 
		redis.pexpire("user:1", 10000000);
		List<String> list = redis.hgetAllAndRefresh("user:1", 100000);
		System.out.println(list);
	}
	
	public void testDelAndSadd()  {
		long value = redis.delAndSadd("set", "1", "2", "3", "2");
		assertEquals(value, 3);
	}
	
	public void testHpaging() {
		List<byte[]> list = redis.hpaging(
				SerializeUtil.RedisUtil.encode("set:article:time:1"), 
				SerializeUtil.RedisUtil.encode(new byte[]{104, 97, 115, 104, 58, 100, 98, 58, 97, 114, 116, 105, 99, 108, 101}), 
				SerializeUtil.RedisUtil.encode(0), 
				SerializeUtil.RedisUtil.encode(10), 
				SerializeUtil.RedisUtil.encode("ZREVRANGE"));
		if (null == list)
			System.out.println("null");
		int total = Integer.valueOf(new String(list.remove(0)));
		System.out.println(total);
		System.out.println(list.size());
		for (byte[] buffer : list)
			System.out.println(buffer);
	}
}
