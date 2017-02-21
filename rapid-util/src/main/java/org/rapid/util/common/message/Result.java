package org.rapid.util.common.message;

import org.rapid.util.common.SerializeUtil;
import org.rapid.util.common.consts.code.ICode;
import org.rapid.util.common.consts.code.ICode.CommonCode;
import org.rapid.util.common.message.IMessage.Message;

/**
 * 可以表示任何调用过程的返回结果(rpc调用、web调用、甚至是方法调用都可以)
 * 
 * @author Ahab
 *
 * @param <T>
 */
public class Result<T> extends Message<T> {

	private static final long serialVersionUID = 4231387477200661362L;
	
	private int code;
	private String desc;
	
	public Result(int code, String desc, T attach) {
		super(attach);
		this.code = code;
		this.desc = desc;
	}
	
	public int getCode() {
		return code;
	}
	
	public String getDesc() {
		return desc;
	}
	
	public static String jsonSuccess() {
		return jsonError(CommonCode.OK);
	}
	
	public static <T> String jsonSuccess(T data) { 
		return jsonError(CommonCode.OK, data);
	}
	
	public static String jsonResult(int code, String desc) {
		return SerializeUtil.JsonUtil.GSON.toJson(new Result<Void>(code, desc, null));
	}
	
	public static <T> String jsonError(ICode code, T data) {
		return SerializeUtil.JsonUtil.GSON.toJson(new Result<T>(code.id(), code.value(), data));
	}
	
	public static String jsonError(ICode code) {
		return SerializeUtil.JsonUtil.GSON.toJson(new Result<Void>(code.id(), code.value(), null));
	}
}
