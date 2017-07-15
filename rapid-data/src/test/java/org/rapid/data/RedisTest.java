package org.rapid.data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.rapid.data.storage.redis.ILuaCmd;
import org.rapid.data.storage.redis.Redis;
import org.rapid.util.common.serializer.SerializeUtil;
import org.rapid.util.common.serializer.impl.ByteProtostuffSerializer;
import org.rapid.util.lang.DateUtil;

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
		redis.hzset("teststs", "1", "body", DateUtil.currentTime(), "tlist", "ulist");
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
	
	public void testHmzset() { 
		Mem[] models = new Mem[4];
		for (int i = 0; i < 4; i++) {
			Mem mem = new Mem();
			mem.setId(i);
			mem.setAge(i + 10);
			mem.setName("test" + i);
			models[i] = mem;
		}
		Map<String, double[]> map = new HashMap<String, double[]>();
		double[] scores = new double[4];
		for (int i = 0; i < 4; i++) 
			scores[i] = i;
		map.put("datazset1", scores);
		scores = new double[4];
		for (int i = 0; i < 4; i++) 
			scores[i] = DateUtil.currentTime();
		map.put("datazset2", scores);
		redis.hmzset("data", models, map, new ByteProtostuffSerializer<Mem>());
	}
	
	public void testHmzsetAlone() { 
		Mem mem = new Mem();
		mem.setId(10);
		mem.setAge(10 + 10);
		mem.setName("test" + 10);
		Map<String, Double> map = new HashMap<String, Double>();
		map.put("datazset1", 10.0);
		map.put("datazset2", Double.valueOf(DateUtil.currentTime()));
		redis.hmzset("data", mem, map, new ByteProtostuffSerializer<Mem>());
	}
	
	public void testHmdel() { 
		List<Integer> list = new ArrayList<Integer>();
		list.add(0);
		list.add(1);
		redis.hmzdel("data", list, "datazset1", "datazset2");
	}
	
	public void testHmdel1() { 
		redis.hmzdel("data", 10, "datazset1", "datazset2");
	}
	
	public void testHmzdrop() {
		redis.hset("data", "100", "ss");
		redis.hmzdrop("data", "datazset1");
	}
	
	public void testHmsset() {
		List<Mem> models = new ArrayList<Mem>();
		for (int i = 10; i < 15; i++) {
			Mem mem = new Mem();
			mem.setId(i);
			mem.setAge(i + 10);
			mem.setName("test" + i);
			models.add(mem);
		}
		redis.hmsset("mem", models, new ByteProtostuffSerializer<Mem>(), "set1", "set2");
	}
	
	public void testHmsset1() {
		Mem mem = new Mem();
		mem.setId(10);
		mem.setAge(20);
		mem.setName("test" + 10);
		redis.hmsset("mem", mem, new ByteProtostuffSerializer<Mem>(), "set1", "set2");
	}
	
	public void testHmsdel() {
		redis.hmsdel("mem", "10", "set1", "set2");
	}
	
	public void testHmsget() {
		List<byte[]> list = redis.hmsget("mem", "set1");
		ByteProtostuffSerializer<Mem> serializer = new ByteProtostuffSerializer<Mem>();
		serializer.setClazz(Mem.class);
		for (byte[] buffer : list) {
			Mem mem = serializer.antiConvet(buffer);
			System.out.println(mem.getName());
		}
	}
	
	public void testHmGet() {
		List<Integer> list = new ArrayList<Integer>();
		list.add(1);
		list.add(2);
		List<byte[]> data = redis.hmget("ssss", list);
		System.out.println(data);
	}
}
