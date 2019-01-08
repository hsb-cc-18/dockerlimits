package com.cloudcomputing.docker.limits.model.io;

import org.assertj.core.api.AbstractAssert;

import javax.validation.ConstraintViolation;
import java.util.Set;
import java.util.stream.Collectors;

public class ConstraintViolationSetAssert extends AbstractAssert<ConstraintViolationSetAssert, Set<? extends ConstraintViolation>> {
    public ConstraintViolationSetAssert(Set<? extends ConstraintViolation> actual) {
        super(actual, ConstraintViolationSetAssert.class);
    }

    public static ConstraintViolationSetAssert assertThat(Set<? extends ConstraintViolation> actual) {
        return new ConstraintViolationSetAssert(actual);
    }

    public ConstraintViolationSetAssert hasViolationOnPath(String path) {
        isNotNull();

        // check condition
        if (!containsViolationWithPath(actual, path)) {
            failWithMessage("There was no violation with path <%s>. Violation paths: <%s>", path, actual.stream()
                                                                                                        .map(Object::toString).collect(
                    Collectors.toList()));
        }

        return this;
    }

    public ConstraintViolationSetAssert hasNoViolations() {
        isNotNull();

        if (!actual.isEmpty()) {
            failWithMessage("Expecting no violations, but there are %s violations: %s", actual.size(), actual.toString());
        }

        return this;
    }

    private boolean containsViolationWithPath(Set<? extends ConstraintViolation> violations, String path) {
        boolean result = false;
        for (ConstraintViolation violation : violations) {
            if (violation.getPropertyPath()
                         .toString()
                         .equals(path)) {
                result = true;
            }
        }
        return result;
    }
}
