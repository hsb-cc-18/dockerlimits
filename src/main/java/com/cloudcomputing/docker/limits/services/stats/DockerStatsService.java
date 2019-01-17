package com.cloudcomputing.docker.limits.services.stats;

import com.cloudcomputing.docker.limits.model.stats.Stats;

import javax.annotation.Nonnull;

public interface DockerStatsService {
    Stats getStats(@Nonnull String containerId);
    Stats getConfig(@Nonnull String containerId);
}
