package com.cloudcomputing.docker.limits.services.resource;

import com.cloudcomputing.docker.limits.model.stats.ResourceDescriptor;

public interface ResourceUsageService {
    ResourceDescriptor sumResourceUsage(String username);
}
