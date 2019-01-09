package com.cloudcomputing.docker.limits.model.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Collection;

public class ContainsLabelKeysValidator implements ConstraintValidator<ContainsKeys, Collection<String>> {

    private static final String LABEL_KV_SEPARATOR = "=";
    String[] requiredKeys;

    @Override
    public void initialize(ContainsKeys constraintAnnotation) {
        requiredKeys = constraintAnnotation.value();
    }

    /**
     * Implements the validation logic.
     * The state of {@code value} must not be altered.
     * <p>
     * This method can be accessed concurrently, thread-safety must be ensured
     * by the implementation.
     *
     * @param value   object to validate
     * @param context context in which the constraint is evaluated
     * @return {@code false} if {@code value} does not pass the constraint
     */
    @Override
    public boolean isValid(Collection<String> value, ConstraintValidatorContext context) {
        if( value == null ) {
            return true;
        }

        boolean isValid = false;
        for( String requiredKey : requiredKeys ) {
            if (value.stream()
                     .filter(s -> s.contains(LABEL_KV_SEPARATOR))
                     .anyMatch(s -> s.split(LABEL_KV_SEPARATOR)[0].equals(requiredKey))) {
                isValid = true;
            }
        }

        if ( !isValid ) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(
                    "{com.cloudcomputing.docker.limits.model.io." +
                            "constraintvalidatorcontext.ContainsLabelKeysValidator.message}"
            )
                             .addConstraintViolation();
        }

        return isValid;
    }
}
