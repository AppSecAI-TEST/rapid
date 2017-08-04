package org.rapid.util.validator;

import java.math.BigInteger;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PowerValidator implements ConstraintValidator<Power, Object> {

	@Override
	public void initialize(Power constraintAnnotation) {}

	@Override
	public boolean isValid(Object value, ConstraintValidatorContext context) {
		BigInteger val = new BigInteger(value.toString());
		BigInteger bi = val.subtract(new BigInteger("1"));
		return val.and(bi).compareTo(new BigInteger("0")) == 0;
	}
}
