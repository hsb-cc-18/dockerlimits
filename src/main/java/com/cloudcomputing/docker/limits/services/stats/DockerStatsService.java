package com.cloudcomputing.docker.limits.services.stats;

import com.cloudcomputing.docker.limits.model.stats.ResourceDescriptor;

import javax.annotation.Nonnull;

public interface DockerStatsService {
    ResourceDescriptor getStats(@Nonnull String containerId);
    ResourceDescriptor getConfig(@Nonnull String containerId);
}
