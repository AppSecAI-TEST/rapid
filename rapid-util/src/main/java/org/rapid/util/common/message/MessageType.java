package org.rapid.util.common.message;

/**
 * 消息类型
 * 
 * @author ahab
 */
public enum MessageType {

	/**
	 * 通知(由系统主动推送给客户端的消息)
	 */
	NOTICE(1),
	
	/**
	 * 响应(客户端请求时返回的信息)
	 */
	RESPONSE(2),
	
	/**
	 * 普通的方法调用或者 rpc 调用返回的结果
	 */
	MESSAGE(3);
	
	private int mark;
	
	private MessageType(int mark) {
		this.mark = mark;
	}
	
	public int mark() {
		return mark;
	}
	
	public static final MessageType match(int type) {
		for (MessageType temp : MessageType.values()) {
			if (temp.mark == type)
				return temp;
		}
		return null;
	}
}
