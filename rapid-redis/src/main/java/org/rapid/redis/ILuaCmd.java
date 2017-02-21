package org.rapid.redis;

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
		
		DEL_IF_EQUALS {
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
