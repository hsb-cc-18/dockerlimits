package com.cloudcomputing.docker.limits.services.resource;

import com.cloudcomputing.docker.limits.services.stats.Stats;

public interface ResourceUsageService {
    Stats summarizeResourceUsage(String username);
}