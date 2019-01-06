package com.cloudcomputing.docker.limits.services.stats;

import javax.annotation.Nonnull;

public interface DockerStatsService {
    String getStats(@Nonnull String containerId);
}
