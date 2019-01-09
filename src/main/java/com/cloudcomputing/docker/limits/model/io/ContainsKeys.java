package com.cloudcomputing.docker.limits.model.io;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({ FIELD, METHOD, PARAMETER, ANNOTATION_TYPE, TYPE_USE })
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = { ContainsLabelKeysValidator.class })
public @interface ContainsKeys {
    String message() default "{com.acme.ContainsKey.message}";
    Class<?>[] groups() default { };
    Class<? extends Payload>[] payload() default { };
    String[] value() default {};
}
