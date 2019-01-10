package com.cloudcomputing.docker.limits.model.fixer;

import com.cloudcomputing.docker.limits.model.io.DockerCompose;

import javax.validation.ConstraintViolation;
import java.util.Set;

public interface DockerComposeFixer {
    DockerCompose fix(Set<ConstraintViolation<DockerCompose>> violations, DockerCompose dockerCompose);
}
