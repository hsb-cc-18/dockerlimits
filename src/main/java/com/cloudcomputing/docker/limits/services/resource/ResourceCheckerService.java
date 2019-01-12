package com.cloudcomputing.docker.limits.services.resource;

import com.cloudcomputing.docker.limits.model.io.DockerCompose;

/**
 * Decides if the requested resources are authorized.
 */
public interface ResourceCheckerService {
    boolean check(DockerCompose dockerCompose);
}
