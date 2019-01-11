package com.cloudcomputing.docker.limits.services.resource;

import com.cloudcomputing.docker.limits.model.io.DockerCompose;

public interface ResourceCheckerService {
    boolean check(DockerCompose dockerCompose);
}
