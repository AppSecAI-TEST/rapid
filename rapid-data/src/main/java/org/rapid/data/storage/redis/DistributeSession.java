package org.rapid.data.storage.redis;

import java.text.MessageFormat;

import org.rapid.data.storage.redis.ILuaCmd.LuaCmd;
import org.rapid.util.common.serializer.SerializeUtil;
import org.rapid.util.common.uuid.AlternativeJdkIdGenerator;
import org.rapid.util.lang.DateUtil;

/**
 * 分布式 session
 * 
 * @author ahab
 *
 * @param <K>
 * @param <V>
 */
public class DistributeSession {

	private static final String SESSION_KEY				= "session:{0}";
	/**
	 * 默认 30 分钟
	 */
	private static final byte[] DEFAULT_INACTIVE_INTERVAL	= SerializeUtil.RedisUtil.encode(String.valueOf(DateUtil.MILLIS_HALF_HOUR));
	
	private Redis redis;
	private String sessionId;
	private byte[] sessionKey;
	private byte[] inactiveInterval = DEFAULT_INACTIVE_INTERVAL;			// 在该时间内没有操作该 session 则 session 失效，单位毫秒

	public DistributeSession(Redis redis) {
		_init(null, redis);
	}
	
	public DistributeSession(String sessionId, Redis redis) {
		_init(sessionId, redis);
	}

	private void _init(String sessionId, Redis redis)  {
		this.redis = redis;
		this.sessionId = null == sessionId ? AlternativeJdkIdGenerator.INSTANCE.generateId().toString() : sessionId;
		this.sessionKey = SerializeUtil.RedisUtil.encode(MessageFormat.format(SESSION_KEY, this.sessionId));
	}
	
	public String put(String key, int value) {
		return put(key, String.valueOf(value));
	}
	
	public String put(String key, String value) {
		byte[] buffer = put(SerializeUtil.RedisUtil.encode(key), SerializeUtil.RedisUtil.encode(value));
		return null == buffer ? null : SerializeUtil.RedisUtil.decode(buffer);
	}
	
	public byte[] put(byte[] key, byte[] value) {
		return redis.invokeLua(LuaCmd.HSET_AND_REFRESH, sessionKey, key, value, inactiveInterval);
	}
	
	public byte[] get(byte[] field) {
		return redis.invokeLua(LuaCmd.HGETALL_AND_REFRESH, sessionKey, field, inactiveInterval);
	}
	
	public String get(String field) {
		byte[] buffer = get(SerializeUtil.RedisUtil.encode(field));
		return null == buffer ? null : SerializeUtil.RedisUtil.decode(buffer);
	}
	
	public byte[] getAndDel(byte[] field) { 
		return redis.invokeLua(LuaCmd.HGET_AND_DEL, sessionKey, field, inactiveInterval);
	}
	
	public String getAndDel(String field) { 
		byte[] buffer = getAndDel(SerializeUtil.RedisUtil.encode(field));
		return null == buffer ? null : SerializeUtil.RedisUtil.decode(buffer);
	}
	
	public Integer getAndDelInt(String field) { 
		byte[] buffer = getAndDel(SerializeUtil.RedisUtil.encode(field));
		return null == buffer ? null : Integer.parseInt(SerializeUtil.RedisUtil.decode(buffer));
	}
	
	public void setInactiveInterval(int inactiveInterval) {
		this.inactiveInterval = SerializeUtil.RedisUtil.encode(String.valueOf(inactiveInterval));
	}
	
	public String sessionId() {
		return sessionId;
	}
}
