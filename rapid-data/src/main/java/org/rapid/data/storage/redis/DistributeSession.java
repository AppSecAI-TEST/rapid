package org.rapid.data.storage.redis;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

import org.rapid.data.storage.redis.ILuaCmd.LuaCmd;
import org.rapid.util.common.SerializeUtil;
import org.rapid.util.common.uuid.AlternativeJdkIdGenerator;

/**
 * 分布式 session
 * 
 * @author ahab
 *
 * @param <K>
 * @param <V>
 */
public class DistributeSession extends HashMap<byte[], byte[]> {

	private static final long serialVersionUID = 2912294151823393421L;

	private static final String SESSION_KEY				= "session:{0}";
	
	private Redis redis;
	private String sessionId;
	private byte[] sessionKey;
	private byte[] inactiveInterval;			// 在该时间内没有操作该 session 则 session 失效，单位毫秒

	public DistributeSession() {
		_init();
	}

	public DistributeSession(int initialCapacity) {
		super(initialCapacity);
		_init();
	}

	public DistributeSession(int initialCapacity, float loadFactor) {
		super(initialCapacity, loadFactor);
		_init();
	}
	
	private void _init()  {
		this.sessionId = AlternativeJdkIdGenerator.INSTANCE.generateId().toString();
		this.sessionKey = SerializeUtil.RedisUtil.encode(MessageFormat.format(SESSION_KEY, this.sessionId));
	}
	
	public String put(String key, String value) {
		byte[] buffer = put(SerializeUtil.RedisUtil.encode(key), SerializeUtil.RedisUtil.encode(value));
		return null == buffer ? null : SerializeUtil.RedisUtil.decode(buffer);
	}
	
	@Override
	public byte[] put(byte[] key, byte[] value) {
		return redis.invokeLua(LuaCmd.HSET_AND_REFRESH, sessionKey, inactiveInterval, key, value);
	}
	
	@Override
	public void putAll(Map<? extends byte[], ? extends byte[]> m) {
		byte[][] data = new byte[m.size() + 2][];
		int index = 0;
		data[index++] = sessionKey;
		data[index++] = inactiveInterval;
		for (Entry<?, ?> entry : m.entrySet()) {
			data[index++] = (byte[]) entry.getKey();
			data[index++] = (byte[]) entry.getValue();
		}
		redis.invokeLua(LuaCmd.HMSET_AND_REFRESH, data);
	}
	
	@Override
	public byte[] get(Object key) {
		Class<?> clazz = key.getClass();
		byte[] field = null;
		if (clazz == String.class)
			field = SerializeUtil.RedisUtil.encode((String) key);
		else if(clazz == byte[].class)
			field = (byte[]) key;
		else
			throw new IllegalArgumentException("DistributeSession only supported byte[] and string type!");
		return redis.invokeLua(LuaCmd.HGETALL_AND_REFRESH, sessionKey, field, inactiveInterval);
	}
	
	public void setRedis(Redis redis) {
		this.redis = redis;
	}
	
	public void setInactiveInterval(int inactiveInterval) {
		this.inactiveInterval = SerializeUtil.RedisUtil.encode(String.valueOf(inactiveInterval));
	}
}
