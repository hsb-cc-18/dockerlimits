package com.cloudcomputing.docker.limits.services.limits;

import com.cloudcomputing.docker.limits.model.stats.ResourceDescriptor;

public interface LimitsQueryService {
    ResourceDescriptor getLimitsForUsername(String username);
}
