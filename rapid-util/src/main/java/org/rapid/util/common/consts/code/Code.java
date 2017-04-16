package org.rapid.util.common.consts.code;

/**
 * 这里预留了 0 ~ 500 个 code 值
 * 
 * @author ahab
 */
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
	 * 禁止操作
	 */
	FORBID(4, "forbid"),
	
	/**
	 * 无效的 token
	 */
	TOKEN_INVALID(5, "token is invalid"),
	
	/**
	 * 用户不存在
	 */
	USER_NOT_EXIST(20, "user not exist"),
	
	/**
	 * 用户已经存在
	 */
	USER_ALREADY_EXIST(21, "user already exist"),
	
	/**
	 * 用户状态已经改变，当获取用户锁失败时返回该错误，表示当前用户的数据正在被修改中
	 */
	USER_STATUS_CHANGED(22, "user status changed"),
	
	/**
	 * 验证码获取CD
	 */
	CAPTCHA_GET_CD(100, "captcha get frequently"),
	
	/**
	 * 验证码获取次数限制
	 */
	CAPTCHA_COUNT_LIMIT(101, "captcha count limit"),
	
	/**
	 * 验证码错误
	 */
	CAPTCHA_ERROR(102, "captcha error"),
	
	/**
	 * 密码错误
	 */
	PWD_ERROR(103, "pwd error"),
	
	/**
	 * 没有重置密码
	 */
	PWD_NOT_RESET(104, "pwd not reset"),
	
	/**
	 * 行政区划跨界了
	 */
	REGION_OUT_OF_BOUNDARY(105, "region out of boundary"),
	
	/**
	 * 身份证已经存在了
	 */
	IDENTITY_ALREADY_EXIST(106, "identity already exist");
	
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
