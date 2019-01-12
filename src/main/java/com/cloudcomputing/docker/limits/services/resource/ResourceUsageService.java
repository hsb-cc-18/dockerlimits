package com.cloudcomputing.docker.limits.services.resource;

import com.cloudcomputing.docker.limits.services.stats.Stats;

import java.util.concurrent.CompletableFuture;

public interface ResourceUsageService {
    CompletableFuture<Stats> sumResourceUsage(String username);
}
