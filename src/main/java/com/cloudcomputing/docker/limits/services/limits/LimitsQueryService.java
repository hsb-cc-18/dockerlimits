package com.cloudcomputing.docker.limits.services.limits;

import com.cloudcomputing.docker.limits.model.stats.Stats;

public interface LimitsQueryService {
    Stats getLimitsForUsername(String username);
}
