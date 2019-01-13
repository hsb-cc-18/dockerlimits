package com.cloudcomputing.docker.limits.services.stats;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.InspectContainerResponse;
import com.github.dockerjava.api.model.Statistics;
import com.github.rozidan.springboot.logger.Loggable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Nonnull;
import java.util.Optional;

@Service
@Loggable
class DockerStatsServiceImpl implements DockerStatsService {

    private Logger logger = LoggerFactory.getLogger(getClass());

    private DockerClient dockerClient;

    @Autowired
    public DockerStatsServiceImpl(DockerClient dockerClient) {
        this.dockerClient = dockerClient;
    }

    @Override
    public Stats getStats(@Nonnull String containerId) {
        Stats stats = null;
        try {
            final SingleStatCallback statsCallback = dockerClient.statsCmd(containerId).exec(new SingleStatCallback());
            final Optional<Statistics> latestStatsOptional = statsCallback.getLatestStatsWithTimeout(3);
            final Statistics latestStats = latestStatsOptional.orElseThrow(() -> new IllegalStateException("No Stats received"));
            final String memory = String.valueOf(latestStats.getMemoryStats().getLimit());

            final long cpuDelta = latestStats.getCpuStats().getCpuUsage().getTotalUsage() - latestStats.getPreCpuStats().getCpuUsage().getTotalUsage();
            final long systemDelta = latestStats.getCpuStats().getSystemCpuUsage() - latestStats.getPreCpuStats().getSystemCpuUsage();
            final Integer cpuPercent = Math.toIntExact(cpuDelta / systemDelta * 100);
            logger.debug("Memory limit (stats): {}", memory);

            return new Stats(memory, cpuPercent);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            logger.error("Failed to read stats", e);
        }

        return null;
    }

    @Override
    public Stats getConfig(@Nonnull String containerId) {
        final InspectContainerResponse exec1 = dockerClient.inspectContainerCmd(containerId).exec();
        final String memory = String.valueOf(exec1.getHostConfig().getMemory());
        final Integer cpuPercent = Math.toIntExact(exec1.getHostConfig().getCpuPercent());
        logger.debug("Memory limit (inspect): {}", memory);

        return new Stats(memory, cpuPercent);
    }
}
