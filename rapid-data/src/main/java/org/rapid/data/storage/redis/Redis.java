package org.rapid.data.storage.redis;

import static org.rapid.util.common.serializer.SerializeUtil.RedisUtil.encode;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.codec.digest.DigestUtils;
import org.rapid.data.storage.redis.ILuaCmd.LuaCmd;
import org.rapid.data.storage.redis.RedisOption.EXPX;
import org.rapid.data.storage.redis.RedisOption.NXXX;
import org.rapid.util.common.Callback;
import org.rapid.util.common.RapidSecurity;
import org.rapid.util.common.model.UniqueModel;
import org.rapid.util.common.serializer.SerializeUtil;
import org.rapid.util.common.serializer.Serializer;
import org.rapid.util.io.FileReader;
import org.rapid.util.lang.StringUtil;
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
				_addLuaScript(file.getName().replaceAll(LUA_SCRIPT_SUFFIX, StringUtil.EMPTY), new String(FileReader.bufferRead(file)));
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
				return jedis.del(encode(keys));
			}
		});
	}
	
	public String set(Object key, Object value, NXXX nxxx, EXPX expx, int time) {
		return invoke(new RedisInvocation<String>() {
			@Override
			public String invok(Jedis jedis) {
				return jedis.set(encode(key), encode(value), encode(nxxx.name()), encode(expx.name()), time);
			}
		});
	}

	// ******************************** hash ********************************
	
	public boolean hdel(Object key, Object... field) { 
		return 1 == invoke(new RedisInvocation<Long>() {
			@Override
			public Long invok(Jedis jedis) {
				return jedis.hdel(encode(key), encode(field));
			}
		});
	}
	
	public byte[] hget(Object key, Object field) {
		return invoke(new RedisInvocation<byte[]>() {
			@Override
			public byte[] invok(Jedis jedis) {
				return jedis.hget(encode(key), encode(field));
			}
		});
	}
	
	public List<byte[]> hmget(Object key, Collection<?> fields) {
		return invoke(new RedisInvocation<List<byte[]>>() {
			@Override
			public List<byte[]> invok(Jedis jedis) {
				return jedis.hmget(encode(key), encode(fields));
			}
		});
	}
	
	public String hmset(Object key, Map<byte[], byte[]> map) {
		return invoke(new RedisInvocation<String>() {
			@Override
			public String invok(Jedis jedis) {
				return jedis.hmset(encode(key), map);
			}
		});
	}
	
	public long hset(Object key, Object field, Object value) { 
		return invoke(new RedisInvocation<Long>() {
			@Override
			public Long invok(Jedis jedis) {
				return jedis.hset(SerializeUtil.RedisUtil.encode(key), SerializeUtil.RedisUtil.encode(field), SerializeUtil.RedisUtil.encode(value));
			}
		});
	}
	
	public boolean hsetnx(Object key, Object field, Object value) {
		return 1 == invoke(new RedisInvocation<Long>() {
			@Override
			public Long invok(Jedis jedis) {
				return jedis.hsetnx(encode(key), encode(field), encode(value));
			}
		});
	}
	
	public List<byte[]> hvals(Object key) {
		return invoke(new RedisInvocation<List<byte[]>>() {
			@Override
			public List<byte[]> invok(Jedis jedis) {
				return jedis.hvals(encode(key));
			}
		});
	}
	
	public Set<byte[]> hkeys(Object key) {
		return invoke(new RedisInvocation<Set<byte[]>>() {
			@Override
			public Set<byte[]> invok(Jedis jedis) {
				return jedis.hkeys(encode(key));
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
	public byte[] hgetJsonValueIfNotExpire(Object key, Object field, long date)  {
		return invokeLua(LuaCmd.HGET_JSON_IF_NOT_EXPIRE, key, field, String.valueOf(date));
	}
	
	public String hgetAndRefresh(String key, String field, int expire) {
		return invokeLua(LuaCmd.HGET_AND_REFRESH, key, field, String.valueOf(expire));
	}
	
	public void hmsetAndRefresh(Object ...params) {
		invokeLua(LuaCmd.HMSET_AND_REFRESH, params);
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
	
	public void hmsdel(Object hashKey, Object member, Object... setKeys) {
		Object[] params = new Object[setKeys.length + 2];
		int idx = 0;
		params[idx++] = hashKey;
		for (Object setKey : setKeys)
			params[idx++] = setKey;
		params[idx++] = member;
		invokeLua(setKeys.length + 1, ILuaCmd.LuaCmd.HMSDEL, params);
	}
	
	public List<byte[]> hmsget(Object hashKey, Object setKey) {
		Object[] params = new Object[2];
		params[0] = hashKey;
		params[1] = setKey;
		return invokeLua(LuaCmd.HMSGET, params);
	}
	
	public <T extends UniqueModel<?>> void hmsset(Object hashKey, T model, Serializer<T, byte[]> serializer, Object... setKeys) {
		Object[] params = new Object[setKeys.length + 3];
		int idx = 0;
		params[idx++] = hashKey;
		for (Object setKey : setKeys)
			params[idx++] = setKey;
		params[idx++] = model.key();
		params[idx++] = serializer.convert(model);
		invokeLua(setKeys.length + 1, ILuaCmd.LuaCmd.HMSSET, params);
	}
	
	public <T extends UniqueModel<?>> void hmsset(Object hashKey, List<T> models, Serializer<T, byte[]> serializer, Object... setKeys) {
		Object[] params = new Object[models.size() * 2 + setKeys.length + 1];
		int idx = 0;
		params[idx++] = hashKey;
		for (Object setKey : setKeys)
			params[idx++] = setKey;
		for (T model : models) {
			params[idx++] = model.key();
			params[idx++] = serializer.convert(model);
		}
		invokeLua(setKeys.length + 1, ILuaCmd.LuaCmd.HMSSET, params);
	}
	
	public void hmzdel(Object redisKey, Object field, Object... zsetKeys) {
		Object[] params = new Object[zsetKeys.length + 2];
		int idx = 0;
		params[idx++] = redisKey;
		for (Object zsetKey : zsetKeys)
			params[idx++] = zsetKey;
		params[idx++] = field;
		invokeLua(zsetKeys.length + 1, ILuaCmd.LuaCmd.HMZDEL, params);
	}
	
	public void hmzdel(Object redisKey, Collection<?> fields, Object... zsetKeys) {
		Object[] params = new Object[fields.size() + zsetKeys.length + 1];
		int idx = 0;
		params[idx++] = redisKey;
		for (Object zsetKey : zsetKeys)
			params[idx++] = zsetKey;
		for (Object field : fields)
			params[idx++] = field;
		invokeLua(zsetKeys.length + 1, ILuaCmd.LuaCmd.HMZDEL, params);
	}
	
	public void hmzdrop(Object redisKey, Object... zsetKeys) {
		if (zsetKeys.length == 0)
			throw new RuntimeException("Must speicfy a set key to drop");
		Object[] params = new Object[zsetKeys.length + 1];
		int idx = 0;
		params[idx++] = redisKey;
		for (Object zsetKey : zsetKeys)
			params[idx++] = zsetKey;
		invokeLua(zsetKeys.length + 1, ILuaCmd.LuaCmd.HMZDROP, params);
	}
	
	public <T extends UniqueModel<?>> void hmzset(Object redisKey, T model, String zsetKey, double score, Serializer<T, byte[]> serializer) {
		Object[] params = new Object[6];
		int idx = 0;
		params[idx++] = redisKey;
		params[idx++] = zsetKey;
		params[idx++] = 1;
		params[idx++] = model.key();
		params[idx++] = serializer.convert(model);
		params[idx++] = score;
		invokeLua(2, ILuaCmd.LuaCmd.HMZSET, params);
	}
	
	public <T extends UniqueModel<?>> void hmzset(Object redisKey, T model, Map<String, Double> zsetParams, Serializer<T, byte[]> serializer) {
		Object[] params = new Object[zsetParams.size() * 2 + 4];
		int idx = 0;
		params[idx++] = redisKey;
		for (String zsetKey : zsetParams.keySet())
			params[idx++] = zsetKey;
		params[idx++] = 1;
		params[idx++] = model.key();
		params[idx++] = serializer.convert(model);
		for (int i = 1; i < zsetParams.size() + 1; i++)
			params[idx++] = zsetParams.get(params[i]);
		invokeLua(zsetParams.size() + 1, ILuaCmd.LuaCmd.HMZSET, params);
	}
	
	public <T extends UniqueModel<?>> void hmzset(Object redisKey, T[] models, Map<String, double[]> zsetParams, Serializer<T, byte[]> serializer) {
		Object[] params = new Object[models.length * 2 + zsetParams.size() * models.length + zsetParams.size() + 2];
		int idx = 0;
		params[idx++] = redisKey;
		for (String zsetKey : zsetParams.keySet())
			params[idx++] = zsetKey;
		params[idx++] = models.length;
		for (int i = 0, len = models.length; i < len; i++) {
			T model = models[i];
			params[idx++] = model.key();
			params[idx++] = serializer.convert(model);
		}
		for (int i = 1; i < zsetParams.size() + 1; i++) {
			double[] scores = zsetParams.get(params[i]);
			for (int j = 0, len = models.length; j < len; j++)
				params[idx++] = scores[j];
		}
		invokeLua(zsetParams.size() + 1, ILuaCmd.LuaCmd.HMZSET, params);
	}
	
	public List<byte[]> hpaging(Object setKey, Object hashKey, Object page, Object pageSize, Object option) {
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
	
	public <T extends UniqueModel<?>> void flush_1_batch(Object key1, Object key2, Map<T, Object> map, Serializer<T, byte[]> serializer) {
		Object[] params = new Object[map.size() * 3 + 2];
		int idx = 0;
		params[idx++] = key1;
		params[idx++] = key2;
		for (Entry<T, Object> entry : map.entrySet()) {
			T model = entry.getKey();
			params[idx++] = model.key();
			params[idx++] = serializer.convert(model);
			params[idx++] = entry.getValue();
		}
		invokeLua(LuaCmd.FLUSH_1_BATCH, params);
	}
	
	public void flush_1(Object key1, Object key2, Object field1, Object data, Object field2) {
		invokeLua(LuaCmd.FLUSH_1, key1, key2, field1, data, field2);
	}
	
	public List<byte[]> hsgetIfMarked(Object controllerKey, Object setKey, Object hashKey, String controllerVal) {
		return invokeLua(LuaCmd.HSGET_IF_MARKED, controllerKey, setKey, hashKey, controllerVal);
	}
	
	public <T extends UniqueModel<?>> void hssetMark(Object controllerKey, Object hashKey, Object setKey, String controllerVal, List<T> models, Serializer<T, byte[]> serializer) {
		Object[] params = new Object[models.size() * 2 + 4];
		int index = 0;
		params[index++] = controllerKey;
		params[index++] = hashKey;
		params[index++] = setKey;
		params[index++] = controllerVal;
		for (UniqueModel<?> model : models) {
			params[index++] = model.key();
			params[index++] = serializer.convert((T) model);
		}
		invokeLua(LuaCmd.HSSET_MARK, params);
	}
	
	public byte[] hzgetDel(Object hashKey, Object field, Object... zsetKeys) { 
		Object[] params = new Object[zsetKeys.length + 2];
		int index = 0;
		params[index++] = hashKey;
		for (Object zsetKey : zsetKeys)
			params[index++] = zsetKey;
		params[index++] = field;
		return invokeLua(zsetKeys.length + 1, LuaCmd.HZGET_DEL, params);
	}
	
	public List<byte[]> hzget(Object zsetkey, Object hashkey, long start, long stop) { 
		return invokeLua(LuaCmd.HZGET, zsetkey, hashkey, start, stop);
	}
	
	public void hzset(Object hashKey, Object field, Object value, Object score, Object... zsetKeys) {
		Object[] params = new Object[zsetKeys.length + 4];
		int index = 0;
		params[index++] = hashKey;
		for (Object zsetKey : zsetKeys)
			params[index++] = zsetKey;
		params[index++] = field;
		params[index++] = value;
		params[index++] = score;
		invokeLua(zsetKeys.length + 1, LuaCmd.HZSET, params);
	}
	
	public byte[] load_1(Object key1, Object key2, Object field) {
		return invokeLua(LuaCmd.LOAD_1, key1, key2, field);
	}
	
	public <T extends UniqueModel<?>> void hsset(Object hashKey, Object key, Object data, String... setKeys) {
		int keyNum = setKeys.length + 1;
		Object[] params = new Object[keyNum + 2];
		int index = 0;
		params[index++] = hashKey;
		for (String setKey : setKeys)
			params[index++] = setKey;
		params[index++] = key;
		params[index++] = data;
		invokeLua(keyNum, LuaCmd.HSSET, params);
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

	public <T> T invokeLua(ILuaCmd cmd, Object... params) {
		LuaScript script = scripts.get(cmd.key());
		if (null == script)
			throw new JedisNoScriptException("Script " + cmd.key() + " not exist!");
		byte[][] arr = encode(params);
		return invoke(new RedisInvocation<T>() {
			@Override
			public T invok(Jedis jedis) {
				if (script.isStored())
					try {
						return (T) jedis.evalsha(encode(script.getSha1Key()), cmd.keyNum(), arr);
					} catch (JedisNoScriptException e) {
						logger.warn("script {} not cached!", cmd.key());
					}

				T object = (T) jedis.eval(encode(script.getContent()), cmd.keyNum(), arr);
				script.setStored(true);
				return object;
			}
		});
	}
	
	public <T> T invokeLua(int keyNum, ILuaCmd cmd, Object... params) {
		LuaScript script = scripts.get(cmd.key());
		if (null == script)
			throw new JedisNoScriptException("Script " + cmd.key() + " not exist!");
		byte[][] arr = encode(params);
		return invoke(new RedisInvocation<T>() {
			@Override
			public T invok(Jedis jedis) {
				if (script.isStored())
					try {
						return (T) jedis.evalsha(encode(script.getSha1Key()), keyNum, arr);
					} catch (JedisNoScriptException e) {
						logger.warn("script {} not cached!", cmd.key());
					}

				T object = (T) jedis.eval(encode(script.getContent()), keyNum, arr);
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
