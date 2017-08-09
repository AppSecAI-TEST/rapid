package org.rapid.util.validator.custom;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.rapid.util.lang.DateUtil;

public class TimestampValidator implements ConstraintValidator<FutureTimestamp, Integer> {
	
	@Override
	public void initialize(FutureTimestamp constraintAnnotation) {}

	@Override
	public boolean isValid(Integer value, ConstraintValidatorContext context) {
		if (null == value)
			return true;
		return DateUtil.currentTime() > value;
	}
}
