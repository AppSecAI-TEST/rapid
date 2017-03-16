package org.rapid.data.storage.redis;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

import org.apache.commons.codec.digest.DigestUtils;
import org.rapid.data.storage.redis.ILuaCmd.LuaCmd;
import org.rapid.data.storage.redis.RedisOption.EXPX;
import org.rapid.data.storage.redis.RedisOption.NXXX;
import org.rapid.util.common.Callback;
import org.rapid.util.common.SerializeUtil;
import org.rapid.util.common.uuid.AlternativeJdkIdGenerator;
import org.rapid.util.io.FileReader;
import org.rapid.util.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisSentinelPool;
import redis.clients.jedis.exceptions.JedisNoScriptException;

public class Redis {

	private static final Logger logger = LoggerFactory.getLogger(Redis.class);

	private static final String DEFAULT_LUA_FILE = "/lua/";
	private static final String LUA_SCRIPT_SUFFIX = ".lua";

	private JedisSentinelPool jedisPool;

	// redis lua 脚本缓存
	private Map<String, LuaScript> scripts = new ConcurrentHashMap<String, LuaScript>();

	public Redis() {
		_loadPredefinedLuaScript();
	}

	/**
	 * 加载预定义的 lua 脚本和自定义的 lua 脚本，注意 lua 脚本名字就是文件名的小写
	 * 
	 * @param luaScriptLocation
	 * @throws IOException
	 */
	public Redis(String luaScriptLocation) throws Exception {
		_loadPredefinedLuaScript();
		FileReader.deepProcess(luaScriptLocation, new FilenameFilter() {
			@Override
			public boolean accept(File dir, String name) {
				return name.endsWith(LUA_SCRIPT_SUFFIX);
			}
		}, new Callback<File, Void>() {
			@Override
			public Void invoke(File file) throws Exception {
				_addLuaScript(file.getName().replaceAll(LUA_SCRIPT_SUFFIX, StringUtils.EMPTY),
						new String(FileReader.bufferRead(file)));
				return null;
			}
		});
	}

	// ******************************** key ********************************
	
	public long pexpire(String key, int milliseconds) {
		return invoke(new RedisInvocation<Long>() {
			@SuppressWarnings("deprecation")
			@Override
			public Long invok(Jedis jedis) {
				return jedis.pexpire(key, milliseconds);
			}
		});
	}

	/**
	 * 以秒为单位返回 key 的生存时间
	 * 
	 * @param key
	 * @return 如果 key 不存在则返回 -2；如果 key 存在但是没有设置生存时间则返回 -1；返回 key 的正数生存时间
	 */
	public long ttl(String key) {
		return invoke(new RedisInvocation<Long>() {
			@Override
			public Long invok(Jedis jedis) {
				return jedis.ttl(key);
			}
		});
	}

	// ******************************** string ********************************

	/**
	 * 删除指定的多个 key
	 * 
	 * @param keys
	 * @return 返回成功删除的 key 的数量
	 */
	public long del(String... keys) {
		return invoke(new RedisInvocation<Long>() {
			@Override
			public Long invok(Jedis jedis) {
				return jedis.del(keys);
			}
		});
	}
	
	public long del(byte[]... keys) {
		return invoke(new RedisInvocation<Long>() {
			@Override
			public Long invok(Jedis jedis) {
				return jedis.del(keys);
			}
		});
	}

	public String get(String key) {
		return invoke(new RedisInvocation<String>() {
			@Override
			public String invok(Jedis jedis) {
				return jedis.get(key);
			}
		});
	}

	public long incr(String key) {
		return invoke(new RedisInvocation<Long>() {
			@Override
			public Long invok(Jedis jedis) {
				return jedis.incr(key);
			}
		});
	}

	/**
	 * 设置 key 的值为 value
	 * 
	 * @param key
	 * @param value
	 * @return 永远返回字符串 "OK"
	 */
	public String set(String key, String value) {
		return invoke(new RedisInvocation<String>() {
			@Override
			public String invok(Jedis jedis) {
				return jedis.set(key, value);
			}
		});
	}
	
	public String set(byte[] key, byte[] value) {
		return invoke(new RedisInvocation<String>() {
			@Override
			public String invok(Jedis jedis) {
				return jedis.set(key, value);
			}
		});
	}
	
	public String setwithoptions(byte[] key, byte[] value, NXXX nxxx, EXPX expx, int time) {
		return invoke(new RedisInvocation<String>() {
			@Override
			public String invok(Jedis jedis) {
				return jedis.set(key, value, nxxx.binary(), expx.binary(), time);
			}
		});
	}
	
	public String setwithoptions(String key, String value, NXXX nxxx, EXPX expx, int time) {
		return invoke(new RedisInvocation<String>() {
			@Override
			public String invok(Jedis jedis) {
				return jedis.set(key, value, nxxx.name(), expx.name(), time);
			}
		});
	}

	/**
	 * 设置 key 的值为 value
	 * 
	 * @param key
	 * @param value
	 * @param nxxx
	 *            {@link NXXX}
	 * @param expx
	 *            {@link EXPX}
	 * @param expire
	 *            过期时间
	 * @return 如果不满足 {@link NXXX} 条件而失败则返回 null，否则返回字符串 "OK"
	 */
	public String set(String key, String value, NXXX nxxx, EXPX expx, long expire) {
		return invoke(new RedisInvocation<String>() {
			@Override
			public String invok(Jedis jedis) {
				return jedis.set(key, value, nxxx.name(), expx.name(), expire);
			}
		});
	}

	// ******************************** hash ********************************

	public byte[] hget(byte[] key, byte[] field) {
		return invoke(new RedisInvocation<byte[]>() {
			@Override
			public byte[] invok(Jedis jedis) {
				return jedis.hget(key, field);
			}
		});
	}
	
	public String hget(String key, String field) {
		return invoke(new RedisInvocation<String>() {
			@Override
			public String invok(Jedis jedis) {
				return jedis.hget(key, field);
			}
		});
	}
	
	public Map<String, String> hgetAll(String key) {
		return invoke(new RedisInvocation<Map<String, String>>() {
			@Override
			public Map<String, String> invok(Jedis jedis) {
				return jedis.hgetAll(key);
			}
		});
	}
	
	public Map<byte[], byte[]> hgetAll(byte[] key) {
		return invoke(new RedisInvocation<Map<byte[], byte[]>>() {
			@Override
			public Map<byte[], byte[]> invok(Jedis jedis) {
				return jedis.hgetAll(key);
			}
		});
	}

	public String hmset(String key, Map<String, String> hash) {
		return invoke(new RedisInvocation<String>() {
			@Override
			public String invok(Jedis jedis) {
				return jedis.hmset(key, hash);
			}
		});
	}

	public String hmset(byte[] key, Map<byte[], byte[]> hash) {
		return invoke(new RedisInvocation<String>() {
			@Override
			public String invok(Jedis jedis) {
				return jedis.hmset(key, hash);
			}
		});
	}
	
	public long hset(String key, String field, String value) { 
		return invoke(new RedisInvocation<Long>() {
			@Override
			public Long invok(Jedis jedis) {
				return jedis.hset(key, field, value);
			}
		});
	}
	
	public long hset(byte[] key, byte[] field, byte[] value) { 
		return invoke(new RedisInvocation<Long>() {
			@Override
			public Long invok(Jedis jedis) {
				return jedis.hset(key, field, value);
			}
		});
	}
	
	public List<byte[]> hvals(byte[] key) {
		return invoke(new RedisInvocation<List<byte[]>>() {
			@Override
			public List<byte[]> invok(Jedis jedis) {
				return jedis.hvals(key);
			}
		});
	}
	
	public List<String> hvals(String key) {
		return invoke(new RedisInvocation<List<String>>() {
			@Override
			public List<String> invok(Jedis jedis) {
				return jedis.hvals(key);
			}
		});
	}

	// ******************************** server command
	// ********************************

	public String flushAll() {
		return invoke(new RedisInvocation<String>() {
			@Override
			public String invok(Jedis jedis) {
				return jedis.flushAll();
			}
		});
	}

	// ******************************** function command ********************************

	/**
	 * 分布式锁：只获取一次
	 * 
	 * @param lock
	 * @param expire
	 * @return 成功则返回分布式锁的唯一ID，否则返回 null
	 */
	public String tryLock(String lock, long expire) {
		String lockId = AlternativeJdkIdGenerator.INSTANCE.generateId().toString();
		String result = set(lock, lockId, NXXX.NX, EXPX.PX, expire);
		return null == result ? null : lockId;
	}

	/**
	 * 分布式锁：超过一定时间没有获得锁则失败
	 * 
	 * @param lock
	 * @param timeoutMillis
	 * @param expire
	 * @return 成功则返回分布式锁的唯一ID，否则返回 null
	 */
	public String lock(String lock, long timeoutMillis, long expire) {
		long begin = System.nanoTime();
		while (true) {
			String lockId = tryLock(lock, expire);
			if (null != lockId)
				return lockId;

			long time = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - begin);
			if (time >= timeoutMillis)
				return null;
			// TODO:
			Thread.yield();
		}
	}

	/**
	 * 释放分布式锁
	 * 
	 * @param lock
	 * @param lockId
	 * @return
	 */
	public boolean unLock(String lock, String lockId) {
		return delIfEquals(lock, lockId);
	}

	// ******************************** lua command
	// ********************************

	/**
	 * 获取验证码
	 * 
	 * @param captchaKey
	 *            验证码 key
	 * @param countKey
	 *            验证码获取次数 key
	 * @param captcha
	 *            验证码
	 * @param lifeTime
	 *            验证码有效时长
	 * @param countMaxinum
	 *            验证码获取最大次数
	 * @param countLiftTime
	 *            验证码次数生命周期(超过该时间没有获取验证码，则验证码次数 key 会被删除，也就是说验证码次数会被清零)
	 * @return 0 - 表示成功；-1 - 表示获取验证码获取太频繁，-2 - 表示验证码获取次数上限
	 */
	public long captchaObtain(String captchaKey, String countKey, String captcha, long lifeTime, long countMaxinum,
			long countLiftTime) {
		return invokeLua(LuaCmd.CAPTCHA_OBTAIN, captchaKey, countKey, captcha, String.valueOf(lifeTime),
				String.valueOf(countMaxinum), String.valueOf(countLiftTime));
	}

	/**
	 * 先删除整个 hash 的值, 再重新加载新的值(因为是原子操作所以没有并发问题)
	 * 
	 * @param params
	 */
	public void delAndHmset(byte[][] params) {
		invokeLua(LuaCmd.DEL_AND_HMSET, params);
	}
	
	public void delAndHmset(String[] params) {
		invokeLua(LuaCmd.DEL_AND_HMSET, params);
	}
	
	/**
	 * 如果 key 值存在并且值等于 value 则删除 value 然后返回 true，否则什么也不做返回 false
	 * 
	 * @param key
	 * @param value
	 * @return
	 */
	public boolean delIfEquals(String key, String value) {
		long flag = invokeLua(LuaCmd.DEL_IF_EQUALS, key, value);
		return flag == 1;
	}
	
	public byte[] getAndDel(String key) { 
		return invokeLua(LuaCmd.GET_AND_DEL, key);
	}
	
	public List<String> hgetAllAndRefresh(String key, int expireMillis) {
		return invokeLua(LuaCmd.HGETALL_AND_REFRESH, key, String.valueOf(expireMillis));
	}
	
	public void refreshHash(String ...params) {
		invokeLua(LuaCmd.REFRESH_HASH, params);
	}

	/**
	 * 加载预定义的 lua 脚本
	 * 
	 */
	private void _loadPredefinedLuaScript() {
		for (LuaCmd cmd : LuaCmd.values()) {
			try {
				byte[] buffer = FileReader.bufferReadFromClassOrJar(Redis.class,
						DEFAULT_LUA_FILE + cmd.key() + LUA_SCRIPT_SUFFIX);
				_addLuaScript(cmd.key(), new String(buffer));
			} catch (IOException e) {
				logger.warn("Lua script {} load failure!", cmd.key());
			}
		}

		logger.info("Total {} lua files are loaded by default!", scripts.size());
	}

	private void _addLuaScript(String key, String content) throws IOException {
		String shalKey = DigestUtils.sha1Hex(content);
		if (null != scripts.putIfAbsent(key, new LuaScript(shalKey, content)))
			logger.warn("Lua script - {}:{} already exist!", key, content);
	}

	/**
	 * 执行 lua 脚本，前 LuaCmd.keyNum() 个参数必须是 redis 的 key
	 * 
	 * @param cmd
	 * @param params
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public <T> T invokeLua(ILuaCmd cmd, String... params) {
		LuaScript script = scripts.get(cmd.key());
		if (null == script)
			throw new JedisNoScriptException("Script " + cmd.key() + " not exist!");

		return invoke(new RedisInvocation<T>() {
			@Override
			public T invok(Jedis jedis) {
				if (script.isStored())
					try {
						return (T) jedis.evalsha(script.getSha1Key(), cmd.keyNum(), params);
					} catch (JedisNoScriptException e) {
						logger.warn("script {} not cached!", cmd.key());
					}
				T object = (T) jedis.eval(script.getContent(), cmd.keyNum(), params);
				script.setStored(true);
				return object;
			}
		});
	}

	@SuppressWarnings("unchecked")
	public <T> T invokeLua(ILuaCmd cmd, byte[]... params) {
		LuaScript script = scripts.get(cmd.key());
		if (null == script)
			throw new JedisNoScriptException("Script " + cmd.key() + " not exist!");

		return invoke(new RedisInvocation<T>() {
			@Override
			public T invok(Jedis jedis) {
				if (script.isStored())
					try {
						return (T) jedis.evalsha(SerializeUtil.RedisUtil.encode(script.getSha1Key()), cmd.keyNum(), params);
					} catch (JedisNoScriptException e) {
						logger.warn("script {} not cached!", cmd.key());
					}

				T object = (T) jedis.eval(SerializeUtil.RedisUtil.encode(script.getContent()), cmd.keyNum(), params);
				script.setStored(true);
				return object;
			}
		});
	}

	/**
	 * 执行普通的 redis 命令
	 * 
	 * @param invoke
	 * @return
	 */
	protected <T> T invoke(RedisInvocation<T> invoke) {
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			return invoke.invok(jedis);
		} finally {
			jedis.close();
		}
	}

	public void setJedisPool(JedisSentinelPool jedisPool) {
		this.jedisPool = jedisPool;
	}

	private interface RedisInvocation<T> {
		T invok(Jedis jedis);
	}
}
