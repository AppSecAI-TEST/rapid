package org.rapid.data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.rapid.data.storage.redis.Redis;
import org.rapid.util.common.serializer.SerializeUtil;
import org.rapid.util.common.serializer.impl.ByteProtostuffSerializer;
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
	
	public void testFlush_1() {
		redis.flush_1(SerializeUtil.RedisUtil.encode("map"), SerializeUtil.RedisUtil.encode("map1"), SerializeUtil.RedisUtil.encode("1"),
				SerializeUtil.RedisUtil.encode("body"), SerializeUtil.RedisUtil.encode("age"));
	}
	
	public void testLoad_1() {
		byte[] buffer = redis.load_1(SerializeUtil.RedisUtil.encode("map1"), SerializeUtil.RedisUtil.encode("map"), SerializeUtil.RedisUtil.encode("ages"));
		if (null == buffer)
			System.out.println("null");
		else
			System.out.println(new String(buffer));
	}
	
	public void testhzset() { 
		redis.hzset("teststs", "1", "body", DateUtils.currentTime(), "tlist", "ulist");
	}
	
	public void testFlush_1_Batch() {
		Map<Mem, Object> map = new HashMap<Mem, Object>();
		for (int i = 0; i < 10; i++) {
			Mem mem = new Mem();
			mem.setId(i);
			mem.setAge(i + 10);
			mem.setName("test" + i);
			map.put(mem, i + 100);
		}
		redis.flush_1_batch("key-1", "key-2", map, new ByteProtostuffSerializer<Mem>());
	}
}
