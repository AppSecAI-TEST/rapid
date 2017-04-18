package org.rapid.util.common.message;

import org.rapid.util.common.consts.code.Code;
import org.rapid.util.common.consts.code.ICode;
import org.rapid.util.common.message.IMessage.Message;
import org.rapid.util.common.serializer.SerializeUtil;

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
	
	public boolean isSuccess() {
		return code == Code.OK.id();
	}
	
	public static <T> Result<T> success() {
		return result(Code.OK);
	}
	
	public static <T> Result<T> result(int code) {
		return result(code, null);
	}
	
	public static <T> Result<T> result(ICode code) {
		return result(code.id(), code.value(), null);
	}
	
	public static <T> Result<T> result(T attach) {
		return result(Code.OK.id(), attach);
	}
	
	public static <T> Result<T> result(int code, T attach) {
		return result(code, null, attach);
	}
	
	public static <T> Result<T> result(ICode code, String desc) {
		return result(code.id(), desc, null);
	}
	
	public static <T> Result<T> result(ICode code, T attach) {
		return result(code.id(), code.value(), attach);
	}
	
	public static <T> Result<T> result(ICode code, String desc, T attach) {
		Result<T> result = new Result<T>();
		result.setCode(code.id());
		result.setDesc(desc);
		result.setAttach(attach);
		return result;
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
		return jsonResult(code.id(), code.value(), null);
	}
	
	public static String jsonResult(int code) {
		return jsonResult(code, null, null);
	}
	
	public static <T> String jsonResult(int code, String desc) {
		return jsonResult(code, desc, null);
	}
	
	public static <T> String jsonResult(int code, T attach) {
		return jsonResult(code, null, attach);
	}
	
	public static <T> String jsonResult(int code, String desc, T attach) {
		return SerializeUtil.JsonUtil.GSON.toJson(result(code, desc, attach));
	}
}
