package org.rapid.util.validator.custom;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.rapid.util.lang.PhoneUtil;

public class MobileValidator implements ConstraintValidator<Mobile, String> {

	@Override
	public void initialize(Mobile constraintAnnotation) {}

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		if (null == value)
			return true;
		try {
			return PhoneUtil.isMobile(value);
		} catch (Exception e) {
			return false;
		}
	}
}
