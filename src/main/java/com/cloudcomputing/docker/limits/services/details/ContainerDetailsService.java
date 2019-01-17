package com.cloudcomputing.docker.limits.services.details;

import com.cloudcomputing.docker.limits.model.details.ContainerDetails;

public interface ContainerDetailsService {
    ContainerDetails getContainerDetails(String containerId);
}
