package com.cloudcomputing.docker.limits.services.resource;

import com.cloudcomputing.docker.limits.model.stats.Stats;

public interface ResourceUsageService {
    Stats sumResourceUsage(String username);
}
