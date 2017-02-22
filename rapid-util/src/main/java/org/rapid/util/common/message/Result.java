package org.rapid.util.common.message;

import org.rapid.util.common.SerializeUtil;
import org.rapid.util.common.consts.code.Code;
import org.rapid.util.common.consts.code.ICode;
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
	
	public int getCode() {
		return code;
	}
	
	public void setCode(int code) {
		this.code = code;
	}
	
	public String getDesc() {
		return desc;
	}
	
	public void setDesc(String desc) {
		this.desc = desc;
	}
	
	public static <T> Result<T> result(int code) {
		return result(code, null);
	}
	
	public static <T> Result<T> result(int code, T attach) {
		return result(code, null, attach);
	}
	
	public static <T> Result<T> result(int code, String desc, T attach) {
		Result<T> result = new Result<T>();
		result.setCode(code);
		result.setDesc(desc);
		result.setAttach(attach);
		return result;
	}
	
	public static String jsonSuccess() {
		return jsonResult(Code.OK.id(), null, null);
	}
	
	public static <T> String jsonSuccess(T attach) { 
		return jsonResult(Code.OK.id(), attach);
	}
	
	public static String jsonResult(ICode code) {
		return jsonResult(code.id(), null, null);
	}
	
	public static String jsonResult(int code) {
		return jsonResult(code, null, null);
	}
	
	public static <T> String jsonResult(int code, T attach) {
		return jsonResult(code, null, attach);
	}
	
	public static <T> String jsonResult(int code, String desc, T attach) {
		return SerializeUtil.JsonUtil.GSON.toJson(result(code, desc, attach));
	}
}
