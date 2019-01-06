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
    public String getStats(@Nonnull String containerId) {
        String hostConfig = "";
        try {
            final SingleStatCallback statsCallback = dockerClient.statsCmd(containerId).exec(new SingleStatCallback());
            final Optional<Statistics> latestStatsOptional = statsCallback.getLatestStatsWithTimeout(3);
            final Statistics latestStats = latestStatsOptional.orElseThrow(() -> new IllegalStateException("No Stats received"));
            logger.debug("Memory limit (stats): {}", latestStats.getMemoryStats().getLimit());

            final InspectContainerResponse exec1 = dockerClient.inspectContainerCmd(containerId).exec();
            logger.debug("Memory limit (inspect): {}", exec1.getHostConfig().getMemory());

            hostConfig = exec1.getHostConfig().toString();
        } catch (Exception e) {
            logger.error("Failed to read stats", e);
        }

        return hostConfig;
    }
}
