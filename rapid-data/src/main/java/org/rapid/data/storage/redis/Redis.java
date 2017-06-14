package org.rapid.data.storage.redis;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.codec.digest.DigestUtils;
import org.rapid.data.storage.redis.ILuaCmd.LuaCmd;
import org.rapid.data.storage.redis.RedisOption.EXPX;
import org.rapid.data.storage.redis.RedisOption.NXXX;
import org.rapid.util.common.Callback;
import org.rapid.util.common.RapidSecurity;
import org.rapid.util.common.converter.Converter;
import org.rapid.util.common.model.UniqueModel;
import org.rapid.util.common.serializer.SerializeUtil;
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
				_addLuaScript(file.getName().replaceAll(LUA_SCRIPT_SUFFIX, StringUtils.EMPTY), new String(FileReader.bufferRead(file)));
				return null;
			}
		});
	}

	// ******************************** key ********************************
	
	// ******************************** string ********************************

	/**
	 * 删除指定的多个 key
	 * 
	 * @param keys
	 * @return 返回成功删除的 key 的数量
	 */
	public long del(Object... keys) {
		return invoke(new RedisInvocation<Long>() {
			@Override
			public Long invok(Jedis jedis) {
				return jedis.del(SerializeUtil.RedisUtil.encode(keys));
			}
		});
	}
	
	public String set(Object key, Object value, NXXX nxxx, EXPX expx, int time) {
		return invoke(new RedisInvocation<String>() {
			@Override
			public String invok(Jedis jedis) {
				return jedis.set(SerializeUtil.RedisUtil.encode(key), SerializeUtil.RedisUtil.encode(value), 
						SerializeUtil.RedisUtil.encode(nxxx.name()), 
						SerializeUtil.RedisUtil.encode(expx.name()), time);
			}
		});
	}

	// ******************************** hash ********************************
	
	public <T> T hget(Object key, Object field, Converter<byte[], T>... converters) {
		byte[] buffer = invoke(new RedisInvocation<byte[]>() {
			@Override
			public byte[] invok(Jedis jedis) {
				return jedis.hget(SerializeUtil.RedisUtil.encode(key), SerializeUtil.RedisUtil.encode(field));
			}
		});
		if (null == buffer || null == converters || converters.length == 0)
			return (T) buffer;
		return converters[0].convert(buffer);
	}
	
	public List<byte[]> hmget(Object key, Object... fields) {
		return invoke(new RedisInvocation<List<byte[]>>() {
			@Override
			public List<byte[]> invok(Jedis jedis) {
				return jedis.hmget(SerializeUtil.RedisUtil.encode(key), SerializeUtil.RedisUtil.encode(fields));
			}
		});
	}
	
	public List<String> hmget(String key, String ... fields) {
		return invoke(new RedisInvocation<List<String>>() {
			@Override
			public List<String> invok(Jedis jedis) {
				return jedis.hmget(key, fields);
			}
		});
	}
	
	/**
	 * 如果指定的 key 中的数据没有失效则获取，否则会删除返回nil
	 * 注意 field 对应的 value 必须是一个 json，且有一个 expire 字段
	 * 
	 * @param key
	 * @param field
	 * @param date
	 * @return
	 */
	public <T> T hgetJsonValueIfNotExpire(String key, String field, long date, Class<T> clazz)  {
		String value = invokeLua(LuaCmd.HGET_IF_NOT_EXPIRE, key, field, String.valueOf(date));
		return null == value ? null : SerializeUtil.JsonUtil.GSON.fromJson(value, clazz);
	}
	
	public String hgetAndRefresh(String key, String field, int expire) {
		return invokeLua(LuaCmd.HGET_AND_REFRESH, key, field, String.valueOf(expire));
	}
	
	public <KEY, T extends UniqueModel<KEY>> String hmsetProtostuff(byte[] key, List<T> list) {
		return invoke(new RedisInvocation<String>() {
			@Override
			public String invok(Jedis jedis) {
				Map<byte[], byte[]> map = new HashMap<byte[], byte[]>(list.size() * 2);
				for (T t : list)
					map.put(SerializeUtil.RedisUtil.encode(t.key()), SerializeUtil.ProtostuffUtil.serial(t));
				return jedis.hmset(key, map);
			}
		});
	}
	
	public void hmsetAndRefresh(String ...params) {
		invokeLua(LuaCmd.HMSET_AND_REFRESH, params);
	}
	
	public boolean hsetnx(String key, String field, String value) { 
		return invoke(new RedisInvocation<Boolean>(){
			@Override
			public Boolean invok(Jedis jedis) {
				return 1 == jedis.hsetnx(key, field, value);
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
	
	public List<byte[]> hmgetByZsetKeys(byte[] zsetkey, byte[] hashkey, long start, long stop) { 
		return invokeLua(LuaCmd.HMGET_BY_ZSET_KEYS, zsetkey, hashkey, start, stop);
	}
	
	// ******************************** set ********************************
	
	// ******************************** sorted set ********************************
	
	public long zadd(String key, double score, String member) { 
		return invoke(new RedisInvocation<Long>() {
			@Override
			public Long invok(Jedis jedis) {
				return jedis.zadd(key, score, member);
			}
		});
	}
	
	public long zadd(String key, Map<String, Double> scoreMembers) { 
		return invoke(new RedisInvocation<Long>() {
			@Override
			public Long invok(Jedis jedis) {
				return jedis.zadd(key, scoreMembers);
			}
		});
	}
	
	// ******************************** server command ********************************

	// ******************************** lua command ********************************

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
	
	public List<byte[]> hpaging(byte[] setKey, byte[] hashKey, byte[] page, byte[] pageSize, byte[] option) {
		return invokeLua(LuaCmd.HPAGING, setKey, hashKey, page, pageSize, option);
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
	
	public void flush_1(Object key1, Object key2, Object field1, Object data, Object field2) {
		invokeLua(LuaCmd.FLUSH_1, key1, key2, field1, data, field2);
	}
	
	public byte[] load_1(Object key1, Object key2, Object field) {
		return invokeLua(LuaCmd.LOAD_1, key1, key2, field);
	}
	
	/**
	 * 成功刷新列表之后将 key 列表返回
	 * 
	 * @param cacheControllerKey
	 * @param hashKey
	 * @param setKey
	 * @param cacheControllerVal
	 * @param models
	 * @return
	 */
	public void protostuffCacheListFlush(String cacheControllerKey, byte[] hashKey, String setKey, String cacheControllerVal, List<? extends UniqueModel<?>> models) {
		Object[] params = new Object[models.size() * 2 + 4];
		int index = 0;
		params[index++] = cacheControllerKey;
		params[index++] = hashKey;
		params[index++] = setKey;
		params[index++] = cacheControllerVal;
		for (UniqueModel<?> model : models) {
			params[index++] = model.key();
			params[index++] = SerializeUtil.ProtostuffUtil.serial(model);
		}
		invokeLua(LuaCmd.CACHE_LIST_FLUSH, params);
	}
	
	/**
	 * 返回列表中的数据实体
	 * 
	 * @param cacheControllerKey
	 * @param setKey
	 * @param hashKey
	 * @param cacheControllerVal
	 * @return
	 */
	public List<byte[]> protostuffCacheListLoadWithData(String cacheControllerKey, String setKey, byte[] hashKey, String cacheControllerVal) {
		return invokeLua(LuaCmd.CACHE_LIST_LOAD, 3, SerializeUtil.RedisUtil.encode(
				cacheControllerKey, setKey, hashKey, cacheControllerKey));
	}
	
	public <T extends UniqueModel<?>> void protostuffCacheFlush(byte[] hashKey, T model, String... setKeys) {
		int keyNum = setKeys.length + 1;
		byte[][] params = new byte[keyNum + 2][];
		int index = 0;
		params[index++] = hashKey;
		for (String setKey : setKeys)
			params[index++] = SerializeUtil.RedisUtil.encode(setKey);
		params[index++] = SerializeUtil.RedisUtil.encode(model.key());
		params[index++] = SerializeUtil.ProtostuffUtil.serial(model);
		invokeLua(LuaCmd.CACHE_FLUSH, keyNum, params);
	}
	
	public String tokenReplace(String userTokenKey, String tokenUserKey, int uid) {
		String token = RapidSecurity.encodeToken(String.valueOf(uid));
		invokeLua(LuaCmd.TOKEN_REPLACE, userTokenKey, tokenUserKey, String.valueOf(uid), token);
		return token;
	}
	
	public long tokenRemove(String userTokenKey, String tokenUserKey, String token, String userLockKey, String lockId, int lockExpire) {
		return invokeLua(LuaCmd.TOKEN_REMOVE, userTokenKey, tokenUserKey, token, userLockKey, lockId, String.valueOf(lockExpire));
	}
	
	public long tokenRemove(String userTokenKey, String tokenUserKey, String token) {
		return invokeLua(LuaCmd.TOKEN_REMOVE, userTokenKey, tokenUserKey, token);
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

	public <T> T invokeLua(ILuaCmd cmd, Object... params) {
		LuaScript script = scripts.get(cmd.key());
		if (null == script)
			throw new JedisNoScriptException("Script " + cmd.key() + " not exist!");
		byte[][] arr = SerializeUtil.RedisUtil.encode(params);
		return invoke(new RedisInvocation<T>() {
			@Override
			public T invok(Jedis jedis) {
				if (script.isStored())
					try {
						return (T) jedis.evalsha(SerializeUtil.RedisUtil.encode(script.getSha1Key()), cmd.keyNum(), arr);
					} catch (JedisNoScriptException e) {
						logger.warn("script {} not cached!", cmd.key());
					}

				T object = (T) jedis.eval(SerializeUtil.RedisUtil.encode(script.getContent()), cmd.keyNum(), arr);
				script.setStored(true);
				return object;
			}
		});
	}
	
	@SuppressWarnings("unchecked")
	public <T> T invokeLua(ILuaCmd cmd, int keyNum, byte[]... params) {
		LuaScript script = scripts.get(cmd.key());
		if (null == script)
			throw new JedisNoScriptException("Script " + cmd.key() + " not exist!");

		return invoke(new RedisInvocation<T>() {
			@Override
			public T invok(Jedis jedis) {
				if (script.isStored())
					try {
						return (T) jedis.evalsha(SerializeUtil.RedisUtil.encode(script.getSha1Key()), keyNum, params);
					} catch (JedisNoScriptException e) {
						logger.warn("script {} not cached!", cmd.key());
					}

				T object = (T) jedis.eval(SerializeUtil.RedisUtil.encode(script.getContent()), keyNum, params);
				script.setStored(true);
				return object;
			}
		});
	}
	
	@SuppressWarnings("unchecked")
	public <T> T invokeLua(ILuaCmd cmd, int keyNum, String... params) {
		LuaScript script = scripts.get(cmd.key());
		if (null == script)
			throw new JedisNoScriptException("Script " + cmd.key() + " not exist!");

		return invoke(new RedisInvocation<T>() {
			@Override
			public T invok(Jedis jedis) {
				if (script.isStored())
					try {
						return (T) jedis.evalsha(script.getSha1Key(), keyNum, params);
					} catch (JedisNoScriptException e) {
						logger.warn("script {} not cached!", cmd.key());
					}
				T object = (T) jedis.eval(script.getContent(), keyNum, params);
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
