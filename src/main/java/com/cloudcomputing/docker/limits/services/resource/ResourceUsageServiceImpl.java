package com.cloudcomputing.docker.limits.services.resource;

import com.cloudcomputing.docker.limits.services.label.DockerLabelService;
import com.cloudcomputing.docker.limits.services.stats.DockerStatsService;
import com.cloudcomputing.docker.limits.services.stats.Stats;
import com.github.rozidan.springboot.logger.Loggable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
@Loggable
public class ResourceUsageServiceImpl implements ResourceUsageService {

    private final DockerLabelService dockerLabelService;
    private final DockerStatsService dockerStatsService;

    @Autowired
    public ResourceUsageServiceImpl(DockerLabelService dockerLabelService, DockerStatsService dockerStatsService) {
        this.dockerLabelService = dockerLabelService;
        this.dockerStatsService = dockerStatsService;
    }

    @Override
    @Async
    public CompletableFuture<Stats> sumResourceUsage(String username) {
        return CompletableFuture.supplyAsync(() -> {
            final List<String> containers = dockerLabelService.getContainers(username);

            Stats sumStat = new Stats("0M", 0);
            for (String container : containers) {
                final Stats stats = dockerStatsService.getStats(container);
                sumStat = sumStat.add(stats);
            }

            return sumStat;
        });
    }
}
