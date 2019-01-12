package com.cloudcomputing.docker.limits.services.resource;

import com.cloudcomputing.docker.limits.model.io.DockerCompose;
import com.cloudcomputing.docker.limits.services.stats.Stats;

/**
 * Sums requested resources in the docker compose file.
 */
public interface DockerComposeRequestedResourcesService {
    Stats getRequestedResources(DockerCompose dockerCompose);
}
