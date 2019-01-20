package com.cloudcomputing.docker.limits.model.fixer;

import com.cloudcomputing.docker.limits.model.io.DockerCompose;

import javax.annotation.Nonnull;
import javax.validation.ConstraintViolation;
import java.util.Set;

public interface DockerComposeFixer {
    /**
     * Apply fixes for violations to DockerCompose object.
     * @param violations violations found on object
     * @param dockerCompose target of fixes
     * @return Fixed object that might still produce violations due unfixable violations
     */
    DockerCompose fix(@Nonnull Set<ConstraintViolation<DockerCompose>> violations, @Nonnull DockerCompose dockerCompose);
}
