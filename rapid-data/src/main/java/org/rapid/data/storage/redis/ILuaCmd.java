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
		 * 将一个对象写入 hash 中，并且将主键写入多个 set 中(可能的话)
		 */
		CACHE_FLUSH {
			@Override
			public int keyNum() {
				throw new UnsupportedOperationException("CACHE_FLUSH has no fixed keys!");
			}
		},
		
		/**
		 * 一次性将多个对象存入 hash 中，并且将 field 写入一个 set 中
		 * 如果 set 已经缓存过了则不会再次触发上面的操作
		 * 
		 */
		CACHE_LIST_FLUSH {
			@Override
			public int keyNum() {
				return 3;
			}
		},
		
		/**
		 * 获取缓存列表数据，如果缓存还没设置则返回 nil，如果缓存设置了但是缓存列表数据为空，则返回空列表；
		 * 
		 */
		CACHE_LIST_LOAD {
			@Override
			public int keyNum() {
				throw new UnsupportedOperationException("CACHE_FLUSH has no fixed keys!");
			}
		},
		
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
		 * 先删除 key，再设置 key 的值为一个 hash
		 */
		DEL_AND_HMSET {
			@Override
			public int keyNum() {
				return 1;
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
		
		GET_AND_DEL {
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
		},
		
		HGET_AND_REFRESH {
			@Override
			public int keyNum() {
				return 1;
			}
		},
		
		HGET_AND_DEL {
			@Override
			public int keyNum() {
				return 1;
			}
		},
		
		DEL_AND_SADD {
			@Override
			public int keyNum() {
				return 1;
			}
		},
		
		SMEMBERS_AND_REFRESH {
			@Override
			public int keyNum() {
				return 1;
			}
		},
		
		SADD_AND_REFRESH {
			@Override
			public int keyNum() {
				return 1;
			}
		},
		
		HKEYS_AND_REFRESH {
			@Override
			public int keyNum() {
				return 1;
			}
		},
		
		HMGET_BY_ZSET_KEYS {
			@Override
			public int keyNum() {
				return 2;
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
		
		HGET_IF_NOT_EXPIRE {
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
