package org.rapid.util.validator.custom;

import java.math.BigInteger;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PowerValidator implements ConstraintValidator<Power, Object> {

	@Override
	public void initialize(Power constraintAnnotation) {}

	@Override
	public boolean isValid(Object value, ConstraintValidatorContext context) {
		if (null == value)
			return true;
		try {
			BigInteger val = new BigInteger(value.toString());
			BigInteger bi = val.subtract(new BigInteger("1"));
			return val.and(bi).compareTo(new BigInteger("0")) == 0;
		} catch (Exception e) {
			return false;
		}
	}
}
