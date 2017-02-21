package org.rapid.util.exception;

/**
 * 当不能将 Source 转换成 target 时会抛出该异常
 * 
 * @author ahab
 *
 */
public class ConvertFailuerException extends RuntimeException {

	private static final long serialVersionUID = 1428067614548049002L;
	
	private Object source;
	private Object target;

	public ConvertFailuerException(Object source, Object target, String message) {
		super(message);
		this.source = source;
		this.target = target;
	}
	
	public ConvertFailuerException(Object source, Object target, String message, Throwable cause) {
		super(message, cause);
		this.source = source;
		this.target = target;
	}
	
	public Object getSource() {
		return source;
	}
	
	public Object getTarget() {
		return target;
	}
}
