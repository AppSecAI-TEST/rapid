package org.rapid.data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.rapid.data.storage.redis.ILuaCmd;
import org.rapid.data.storage.redis.Redis;
import org.rapid.data.storage.redis.RedisOption.EXPX;
import org.rapid.data.storage.redis.RedisOption.NXXX;
import org.rapid.util.common.serializer.SerializeUtil;
import org.rapid.util.lang.DateUtils;

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
				SerializeUtil.RedisUtil.encode("testset"), 
				SerializeUtil.RedisUtil.encode("testmap"), 
				SerializeUtil.RedisUtil.encode("1"), 
				SerializeUtil.RedisUtil.encode("2"), 
				SerializeUtil.RedisUtil.encode("ZREVRANGE"));
		for (byte[] buffer : list) {
			System.out.println(new String(buffer));
		}
	}
	
	public void test() { 
		Object value = redis.invokeLua(ILuaCmd.LuaCmd.TEST, "set:tenant:1000:apply:list", "hash:tenant:1000:apply", "1", "2", "ZREVRANGE");
		System.out.println(value);
	}
}
