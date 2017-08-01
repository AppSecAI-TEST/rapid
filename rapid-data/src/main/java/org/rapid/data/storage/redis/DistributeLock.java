package org.rapid.data.storage.redis;

import java.util.concurrent.TimeUnit;

import org.rapid.data.storage.redis.RedisOption.EXPX;
import org.rapid.data.storage.redis.RedisOption.NXXX;
import org.rapid.util.common.uuid.AlternativeJdkIdGenerator;
import org.rapid.util.common.uuid.IdGenerator;

/**
 * 分布式锁
 * 
 * @author ahab
 */
public class DistributeLock {

	private Redis redis;
	private int lockTimeout = 3000;													// 锁的有效时间默认为 3 秒钟
	private IdGenerator lockIdGenerator = new AlternativeJdkIdGenerator();			// 用来生成分布式锁Id
	
	public String tryLock(String lock) { 
		String lockId = lockIdGenerator.generateId().toString();
		String result = redis.set(lock, lockId, NXXX.NX, EXPX.PX, lockTimeout);
		if (null != result && result.equalsIgnoreCase(RedisConsts.OK))
			return lockId;
		return null;
	}
	
	public String lock(String lock) {
		long begin = System.nanoTime();
		while (true) {
			String lockId = tryLock(lock);
			if (null != lockId)
				return lockId;
			
			long time = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - begin);
			if (time >= lockTimeout)
				return null;
			Thread.yield();
		}
	}
	
	public String lock(String lock, long timeout) {
		long begin = System.nanoTime();
		while (true) {
			String lockId = tryLock(lock);
			if (null != lockId)
				return lockId;
			
			long time = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - begin);
			if (time >= timeout)
				return null;
			Thread.yield();
		}
	}
	
	public boolean unLock(String lock, String lockId) {
		return redis.delIfEquals(lock, lockId);
	}
	
	public void setLockTimeout(int lockTimeout) {
		this.lockTimeout = lockTimeout;
	}
	
	public void setLockIdGenerator(IdGenerator lockIdGenerator) {
		this.lockIdGenerator = lockIdGenerator;
	}
	
	public void setRedis(Redis redis) {
		this.redis = redis;
	}
}
