package org.rapid.util.common.consts.code;

import org.rapid.util.common.consts.Const;

/**
 * 0 ~ 100 是系统预错误码
 * 
 * @author ahab
 */
public interface ICode extends Const<String> {

	enum CommonCode implements ICode {
		
		/**
		 * 成功
		 */
		OK(0, "success"),
		
		/**
		 * 系统错误
		 */
		SYSTEM_ERROR(1, "system error"),
		
		/**
		 * 缺少参数
		 */
		PARAM_MISS(2, "param {0} miss"),
		
		/**
		 * 参数错误
		 */
		PARAM_ERROR(3, "param {0} error");
		
		private int code;
		private String desc;
		
		private CommonCode(int id, String desc) {
			this.code = id;
			this.desc = desc;
		}

		@Override
		public int id() {
			return code;
		}

		@Override
		public String key() {
			throw new UnsupportedOperationException("Code has no key attribute!");
		}

		@Override
		public String value() {
			return desc;
		}
	}
}
