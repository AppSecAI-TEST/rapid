package org.rapid.util.validator.custom;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ClassNameValidator implements ConstraintValidator<ClassName, String> {

	@Override
	public void initialize(ClassName constraintAnnotation) {}

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		try {
			Class.forName(value);
		} catch (Exception e) {
			return false;
		}
		return true;
	}
}
