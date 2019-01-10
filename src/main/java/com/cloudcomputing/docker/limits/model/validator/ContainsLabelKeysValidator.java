package com.cloudcomputing.docker.limits.model.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Collection;

public class ContainsLabelKeysValidator implements ConstraintValidator<ContainsLabelKeys, Collection<String>> {
    private static final String LABEL_KV_SEPARATOR = "=";
    String[] requiredKeys;
    String messageTemplate;

    @Override
    public void initialize(ContainsLabelKeys constraintAnnotation) {
        requiredKeys = constraintAnnotation.value();
        messageTemplate = constraintAnnotation.message();
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
            return false;
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
            context.buildConstraintViolationWithTemplate(messageTemplate).addConstraintViolation();
        }

        return isValid;
    }
}
