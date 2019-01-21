package com.cloudcomputing.docker.limits.services.resource;

import com.cloudcomputing.docker.limits.model.stats.ResourceDescriptor;
import com.cloudcomputing.docker.limits.services.label.DockerLabelService;
import com.cloudcomputing.docker.limits.services.stats.DockerStatsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ResourceUsageServiceImpl implements ResourceUsageService {

    private final DockerLabelService dockerLabelService;
    private final DockerStatsService dockerStatsService;

    @Autowired
    public ResourceUsageServiceImpl(DockerLabelService dockerLabelService, DockerStatsService dockerStatsService) {
        this.dockerLabelService = dockerLabelService;
        this.dockerStatsService = dockerStatsService;
    }

    @Override
    public ResourceDescriptor sumResourceUsage(String username) {
        final List<String> containers = dockerLabelService.getContainers(username);

        ResourceDescriptor sumStat = ResourceDescriptor.initial();
        for (String container : containers) {
            final ResourceDescriptor resourceDescriptor = dockerStatsService.getStats(container);
            sumStat = sumStat.add(resourceDescriptor);
        }

        return sumStat;
    }
}
