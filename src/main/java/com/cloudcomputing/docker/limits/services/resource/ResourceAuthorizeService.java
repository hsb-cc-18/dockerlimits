package com.cloudcomputing.docker.limits.services.resource;

import com.cloudcomputing.docker.limits.model.io.DockerCompose;

/**
 * Decides if the requested resources are authorized.
 */
public interface ResourceAuthorizeService {
    boolean isAuthorized(DockerCompose dockerCompose);
}
