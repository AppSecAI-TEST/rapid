package org.rapid.util.common.consts.code;

public enum Code implements ICode {

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
	PARAM_ERROR(3, "param {0} error"),
	
	/**
	 * 验证码获取CD
	 */
	CAPTCHA_GET_CD(100, "captcha get frequently"),
	
	/**
	 * 验证码获取次数限制
	 */
	CAPTCHA_COUNT_LIMIT(101, "captcha count limit");
	
	private int code;
	private String desc;
	
	private Code(int id, String desc) {
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
