package org.rapid.util.validator;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PowerValidator.class)
@Documented
public @interface Power {
	
	String message() default "{org.btkj.power}";
	
	Class<?>[] groups() default {};
	
	Class<? extends Payload>[] payload() default {};
}
