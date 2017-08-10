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
@Constraint(validatedBy = TimestampValidator.class)
@Documented
public @interface FutureTimestamp {

	String message() default "{org.btkj.futureTimestamp}";
	
	Class<?>[] groups() default {};
	
	Class<? extends Payload>[] payload() default {};
}
