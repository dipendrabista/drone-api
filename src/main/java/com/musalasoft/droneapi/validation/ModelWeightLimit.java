package com.musalasoft.droneapi.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {ModelWeightLimitValidator.class})
public @interface ModelWeightLimit {
    String message() default "Invalid weight limit for the model";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
