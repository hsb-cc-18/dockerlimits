package com.cloudcomputing.docker.limits.services.stats;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.CreateContainerResponse;
import com.github.dockerjava.api.command.ExecCreateCmdResponse;
import com.github.dockerjava.api.command.InspectContainerResponse;
import com.github.dockerjava.api.model.HostConfig;
import com.github.dockerjava.api.model.Statistics;
import com.github.rozidan.springboot.logger.Loggable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Loggable
class DockerStatsServiceImpl implements DockerStatsService {

    private DockerClient dockerClient;

    @Autowired
    public DockerStatsServiceImpl(DockerClient dockerClient) {
        this.dockerClient = dockerClient;
    }

    @Override
    public String getStats() {

        String containerName = "mybusybox";
        CreateContainerResponse container = dockerClient.createContainerCmd("busybox").withCmd("sleep", "9999")
                                                  .withName(containerName).withHostConfig(HostConfig.newHostConfig().withMemory(100000000L)).exec();
        System.out.println("Created container " + container.toString());

        dockerClient.startContainerCmd(container.getId()).exec();

        ExecCreateCmdResponse exec = dockerClient.execCreateCmd(container.getId()).withAttachStdout(true).withAttachStderr(true).withCmd("/bin/bash").exec();

        System.out.println("Created exec " + exec.toString());
        String hostConfig = "";
        try {
            final SingleStatCallback statsCallback = dockerClient.statsCmd(container.getId()).exec(new SingleStatCallback());
            final Optional<Statistics> latestStatsOptional = statsCallback.getLatestStatsWithTimeout(3);
            final Statistics latestStats = latestStatsOptional.orElseThrow(() -> new IllegalStateException("No Stats received"));
            System.out.println("Memory limit (stats):" + latestStats.getMemoryStats().getLimit());

            final InspectContainerResponse exec1 = dockerClient.inspectContainerCmd(container.getId()).exec();
            System.out.println("Memory limit:" + exec1.getHostConfig().getMemory());

            hostConfig = exec1.getHostConfig().toString();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            dockerClient.removeContainerCmd(container.getId()).withForce(true).exec();
        }

        return hostConfig;
    }
}
