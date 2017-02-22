package org.rapid.util.common.message;

import java.io.Serializable;

/**
 * 所有交互的中间消息接口
 * 
 * @author ahab
 *
 */
public interface IMessage<T> extends Serializable {

	/**
	 * 创建时间
	 * 
	 * @return
	 */
	long createTime();

	/**
	 * 消息类型
	 * 
	 * @return
	 */
	MessageType messageType();
	
	/**
	 * 该消息所携带的具体数据
	 * 
	 * @return
	 */
	T attach();
	
	/**
	 * 默认的消息类型是响应消息
	 * 
	 * @author ahab
	 *
	 * @param <T>
	 */
	public class Message<T> implements IMessage<T> {

		private static final long serialVersionUID = -1888861088157369308L;
		
		private T attach;
		private long createTime;
		private int messageType;
		
		public Message() {
			this.createTime = System.currentTimeMillis();
		}
		
		@Override
		public T attach() {
			return this.attach;
		}
		
		public void setAttach(T attach) {
			this.attach = attach;
		}
		
		@Override
		public long createTime() {
			return this.createTime;
		}

		@Override
		public MessageType messageType() {
			return MessageType.match(this.messageType);
		}
		
		public void setMessageType(int messageType) {
			this.messageType = messageType;
		}
	}
}
