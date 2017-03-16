package org.rapid.data.storage.redis;

/**
 * Lua 脚本名，一般就是文件名
 * 
 * @author ahab
 */
public interface ILuaCmd {

	/**
	 * lua 脚本的名字
	 * 
	 * @return
	 */
	String key();

	/**
	 * lua 脚本需要传递的 key 的个数
	 * 
	 * @return
	 */
	int keyNum();

	enum LuaCmd implements ILuaCmd {
		
		/**
		 * 获取验证码
		 */
		CAPTCHA_OBTAIN {
			@Override
			public int keyNum() {
				return 2;
			}
		},
		
		/**
		 * 如果值等于指定值，则删除，否则返回 0
		 */
		DEL_IF_EQUALS {
			@Override
			public int keyNum() {
				return 1;
			}
		},
		
		/**
		 * 先删除 key，再设置 key 的值为一个 hash
		 */
		DEL_AND_HMSET {
			@Override
			public int keyNum() {
				return 1;
			}
		},
		
		/**
		 * 获取 hash 类型实体，并且刷新缓存的有效时间
		 */
		HGETALL_AND_REFRESH {
			@Override
			public int keyNum() {
				return 1;
			}
		},
		
		REFRESH_HASH {
			@Override
			public int keyNum() {
				return 1;
			}
		},
		
		GET_AND_DEL {
			@Override
			public int keyNum() {
				return 1;
			}
		},
		
		HSET_AND_REFRESH {
			@Override
			public int keyNum() {
				return 1;
			}
		},
		
		HMSET_AND_REFRESH {
			@Override
			public int keyNum() {
				return 1;
			}
		};
		
		@Override
		public String key() {
			return name().toLowerCase();
		}
	}
}
