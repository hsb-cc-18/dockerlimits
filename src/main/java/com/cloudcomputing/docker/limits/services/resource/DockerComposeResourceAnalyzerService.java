package com.cloudcomputing.docker.limits.services.resource;

import com.cloudcomputing.docker.limits.model.io.DockerCompose;
import com.cloudcomputing.docker.limits.services.stats.Stats;

import java.util.concurrent.CompletableFuture;

/**
 * Sums requested resources in the docker compose file.
 */
public interface DockerComposeResourceAnalyzerService {
    CompletableFuture<Stats> sumResources(DockerCompose dockerCompose);
}
