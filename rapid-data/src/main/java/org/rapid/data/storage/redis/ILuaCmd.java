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
		 * 获取 hash 类型实体，并且刷新缓存的有效时间
		 */
		HGETALL_AND_REFRESH {
			@Override
			public int keyNum() {
				return 1;
			}
		},
		
		HZGET_DEL {
			@Override
			public int keyNum() {
				throw new UnsupportedOperationException("HZGET_DEL has no fixed keys!");
			}
		},
		
		HZGET {
			@Override
			public int keyNum() {
				return 2;
			}
		},
		
		HZSET {
			@Override
			public int keyNum() {
				throw new UnsupportedOperationException("HZSET has no fixed keys!");
			}
			
		},
		
		HSET_AND_REFRESH {
			@Override
			public int keyNum() {
				return 1;
			}
		},
		
		HMSDEL {
			@Override
			public int keyNum() {
				throw new UnsupportedOperationException("HMSDEL has no fixed keys!");
			}
		},
		
		HGET_AND_DEL {
			@Override
			public int keyNum() {
				return 1;
			}
		},
		
		HMSGET {
			@Override
			public int keyNum() {
				return 2;
			}
		},
		
		HMZDEL {
			@Override
			public int keyNum() {
				throw new UnsupportedOperationException("HMZDEL has no fixed keys!");
			}
		},
		
		HMSSET {
			@Override
			public int keyNum() {
				throw new UnsupportedOperationException("HMSSET has no fixed keys!");
			}
		},
		
		HMZDROP {
			@Override
			public int keyNum() {
				throw new UnsupportedOperationException("HMZDROP has no fixed keys!");
			}
		},
		
		HMZSET {
			@Override
			public int keyNum() {
				throw new UnsupportedOperationException("HMZSET has no fixed keys!");
			}
		},
		
		/**
		 * hash 分页：数据存放在 hash 中，sorted set 中只存放 hash 的 各个 field
		 */
		HPAGING {
			@Override
			public int keyNum() {
				return 2;
			}
		},
		
		HGET_JSON_IF_NOT_EXPIRE {
			@Override
			public int keyNum() {
				return 1;
			}
		},
		
		TOKEN_REPLACE {
			@Override
			public int keyNum() {
				return 2;
			}
		},
		
		TOKEN_REMOVE {
			@Override
			public int keyNum() {
				return 2;
			}
		};
		
		@Override
		public String key() {
			return name().toLowerCase();
		}
	}
}
