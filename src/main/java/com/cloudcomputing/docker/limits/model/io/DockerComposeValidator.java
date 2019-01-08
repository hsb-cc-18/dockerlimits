package com.cloudcomputing.docker.limits.model.io;

import javax.validation.ConstraintViolation;
import java.util.Set;

public interface DockerComposeValidator {
    Set<ConstraintViolation<DockerCompose>> validate(DockerCompose dockerCompose);
}
