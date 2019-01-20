package com.cloudcomputing.docker.limits.services.details;

import com.cloudcomputing.docker.limits.model.details.ContainerDetails;
import com.cloudcomputing.docker.limits.services.stats.DockerStatsService;
import com.cloudcomputing.docker.limits.services.stats.SingleStatCallback;
import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.InspectContainerResponse;
import com.github.dockerjava.api.model.Statistics;
import com.github.rozidan.springboot.logger.Loggable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Optional;

@Service
@Loggable
public class ContainerDetailsServiceImpl implements ContainerDetailsService {

    private final DockerClient dockerClient;
    private final DockerStatsService dockerStatsService;

    @Autowired
    public ContainerDetailsServiceImpl(DockerClient dockerClient, DockerStatsService dockerStatsService) {
        this.dockerClient = dockerClient;
        this.dockerStatsService = dockerStatsService;
    }

    @Override
    public ContainerDetails getContainerDetails(String containerId) {
        try {
            final SingleStatCallback statsCallback = dockerClient.statsCmd(containerId).exec(new SingleStatCallback());
            final Optional<Statistics> latestStatsOptional = statsCallback.getLatestStatsWithTimeout(3);
            statsCallback.close();
            final Statistics latestStats = latestStatsOptional.orElseThrow(() -> new IllegalStateException("No Stats received"));
            final InspectContainerResponse inspect = dockerClient.inspectContainerCmd(containerId).exec();
            return buildContainerDetails(latestStats, inspect);
        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
        }
        throw new IllegalStateException("Something went wrong");
    }

    private ContainerDetails buildContainerDetails(Statistics statistics, InspectContainerResponse inspect) {
        final String id = inspect.getId().substring(0, 12);
        final String name = inspect.getName();
        final String created = inspect.getCreated();
        final String image = dockerClient.inspectImageCmd(inspect.getImageId()).exec().getConfig().getImage();

        final long cpuDelta = statistics.getCpuStats().getCpuUsage().getTotalUsage() - statistics.getPreCpuStats().getCpuUsage().getTotalUsage();
        final long systemDelta = statistics.getCpuStats().getSystemCpuUsage() - statistics.getPreCpuStats().getSystemCpuUsage();
        final Long cpu_percent = cpuDelta / systemDelta * 100;

        final Long memory_usage = statistics.getMemoryStats().getUsage();
        final Long memory_limit = statistics.getMemoryStats().getLimit();

        return new ContainerDetails(id, name, created, image, cpu_percent, memory_usage, memory_limit);
    }

}
