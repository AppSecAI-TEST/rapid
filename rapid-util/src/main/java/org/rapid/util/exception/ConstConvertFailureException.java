package org.rapid.util.exception;

import org.rapid.util.common.consts.Const;

public class ConstConvertFailureException extends ConvertFailuerException {

	private static final long serialVersionUID = 8901815837258754841L;
	
	private boolean nil;
	private Const<?> constant;
	
	public ConstConvertFailureException(Const<?> constant) {
		this(constant, false);
	}
	
	public ConstConvertFailureException(Const<?> constant, boolean nil) {
		super(null, null, "");
		this.nil = nil;
		this.constant = constant;
	}
	
	public ConstConvertFailureException(Const<?> constant, Throwable cause) {
		super(null, null, "", cause);
		this.constant = constant;
	}
	
	public boolean isNil() {
		return nil;
	}
	
	@SuppressWarnings("unchecked")
	public <T> Const<T> constant() {
		return (Const<T>) constant;
	}
	
	public static final ConstConvertFailureException nullConstException(Const<?> constant) { 
		throw new ConstConvertFailureException(constant, true);
	}
	
	public static final ConstConvertFailureException errorConstException(Const<?> constant) { 
		throw new ConstConvertFailureException(constant, false);
	}
}
