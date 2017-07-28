package org.rapid.util.exception;

import java.text.MessageFormat;

import org.rapid.util.common.consts.Const;
import org.rapid.util.common.consts.code.Code;

public class ConstConvertFailureException extends ConvertFailuerException {

	private static final long serialVersionUID = 8901815837258754841L;
	
	private String desc;
	private boolean miss;
	private Const<?> constant;
	
	public ConstConvertFailureException() {}
	
	public ConstConvertFailureException(Const<?> constant) {
		this(constant, false);
	}
	
	public ConstConvertFailureException(Const<?> constant, boolean miss) {
		super(null, null, "");
		this.miss = miss;
		this.constant = constant;
	}
	
	public ConstConvertFailureException(Const<?> constant, Throwable cause) {
		super(null, null, "", cause);
		this.constant = constant;
	}
	
	public void setDesc(String desc) {
		this.desc = desc;
	}
	
	public String desc() {
		return null != desc ? desc : MessageFormat.format(miss ? Code.PARAM_MISS.value() : Code.PARAM_ERROR.value(), constant.key());
	}
	
	@SuppressWarnings("unchecked")
	public <T> Const<T> constant() {
		return (Const<T>) constant;
	}
	
	public static final ConstConvertFailureException nullConstException(Const<?> constant) { 
		return new ConstConvertFailureException(constant, true);
	}
	
	public static final ConstConvertFailureException errorConstException(Const<?> constant) { 
		return new ConstConvertFailureException(constant, false);
	}
	
	public static final ConstConvertFailureException errorConstException(Const<?> constant, Throwable cause) { 
		return new ConstConvertFailureException(constant, cause);
	}
}
