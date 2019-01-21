package com.cloudcomputing.docker.limits.services.stats;

import com.cloudcomputing.docker.limits.model.stats.ResourceDescriptor;
import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.InspectContainerResponse;
import com.github.dockerjava.api.model.Statistics;
import com.github.rozidan.springboot.logger.Loggable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Nonnull;
import java.io.IOException;
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
    public ResourceDescriptor getStats(@Nonnull String containerId) {
        ResourceDescriptor resourceDescriptor = null;
        try {
            final SingleStatCallback statsCallback = dockerClient.statsCmd(containerId).exec(new SingleStatCallback());
            final Optional<Statistics> latestStatsOptional = statsCallback.getLatestStatsWithTimeout(3);
            statsCallback.close();
            final Statistics latestStats = latestStatsOptional.orElseThrow(() -> new IllegalStateException("No ResourceDescriptor received"));
            final String memory = String.valueOf(latestStats.getMemoryStats().getLimit());

            final long cpuDelta = latestStats.getCpuStats().getCpuUsage().getTotalUsage() - latestStats.getPreCpuStats().getCpuUsage().getTotalUsage();
            final long systemDelta = latestStats.getCpuStats().getSystemCpuUsage() - latestStats.getPreCpuStats().getSystemCpuUsage();
            final Integer cpuPercent = Math.toIntExact(cpuDelta / systemDelta * 100);
            logger.debug("Memory limit (stats): {}", memory);

            //TODO:
            final int blkio_weight = 0;

            return new ResourceDescriptor(memory, cpuPercent, blkio_weight);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            logger.error("Failed to read stats", e);
        } catch (IOException e) {
            logger.error("Failed to close callback", e);
        }

        return null;
    }

    @Override
    public ResourceDescriptor getConfig(@Nonnull String containerId) {
        final InspectContainerResponse exec1 = dockerClient.inspectContainerCmd(containerId).exec();
        final String memory = String.valueOf(exec1.getHostConfig().getMemory());
        final Integer cpu_shares = Math.toIntExact(exec1.getHostConfig().getCpuShares());
        final Integer blkio_weight = Math.toIntExact(exec1.getHostConfig().getBlkioWeight());
        logger.debug("Memory limit (inspect): {}", memory);
        logger.debug("CPU shares limit (inspect): {}", cpu_shares);
        logger.debug("Blkio Weight limit (inspect): {}", blkio_weight);

        return new ResourceDescriptor(memory, cpu_shares, blkio_weight);
    }
}
