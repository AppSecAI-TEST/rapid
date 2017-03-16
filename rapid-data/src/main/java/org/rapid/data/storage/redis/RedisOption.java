package org.rapid.data.storage.redis;

import org.rapid.util.common.SerializeUtil;

/**
 * Redis 的选项常量
 * 
 * @author ahab
 *
 */
public interface RedisOption {
	
	enum NXXX implements RedisOption {
		/**
		 * 当且仅当 key 不存在时设置
		 */
		NX,
		
		/**
		 * 当且仅当 key 存在时设置
		 */
		XX;
		
		public byte[] binary() {
			return SerializeUtil.RedisUtil.encode(this.name());
		}
	}
	
	enum EXPX implements RedisOption {
		
		/**
		 * 秒为单位设置生命周期
		 */
		EX,
		
		/**
		 * 毫秒为单位设置生命周期
		 */
		PX;
		
		public byte[] binary() {
			return SerializeUtil.RedisUtil.encode(this.name());
		}
	}
}
