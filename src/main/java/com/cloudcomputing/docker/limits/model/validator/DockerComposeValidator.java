package com.cloudcomputing.docker.limits.model.validator;

import com.cloudcomputing.docker.limits.model.io.DockerCompose;

import javax.validation.ConstraintViolation;
import java.util.Set;

public interface DockerComposeValidator {
    Set<ConstraintViolation<DockerCompose>> validate(DockerCompose dockerCompose);
}
