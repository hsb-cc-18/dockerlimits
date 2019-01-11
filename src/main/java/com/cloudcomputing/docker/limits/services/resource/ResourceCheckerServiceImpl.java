package com.cloudcomputing.docker.limits.services.resource;

import com.cloudcomputing.docker.limits.model.io.DockerCompose;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ResourceCheckerServiceImpl implements ResourceCheckerService {

    private final ResourceUsageService resourceCheckerService;

    @Autowired
    public ResourceCheckerServiceImpl(ResourceUsageService resourceCheckerService) {
        this.resourceCheckerService = resourceCheckerService;
    }

    @Override
    public boolean check(DockerCompose dockerCompose) {

        resourceCheckerService.summarizeResourceUsage(dockerCompose.getHsbUsername());

        return false;
    }

}
