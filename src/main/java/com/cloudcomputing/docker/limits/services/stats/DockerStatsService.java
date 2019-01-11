package com.cloudcomputing.docker.limits.services.stats;

import javax.annotation.Nonnull;

public interface DockerStatsService {
    Stats getStats(@Nonnull String containerId);
}
