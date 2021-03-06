package org.rapid.util.validator.custom;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ClassNameValidator.class)
@Documented
public @interface ClassName {

	String message() default "{org.btkj.class.name}";
	
	Class<?>[] groups() default {};
	
	Class<? extends Payload>[] payload() default {};
}
