package com.cloudcomputing.docker.limits.services.stats;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.CreateContainerResponse;
import com.github.dockerjava.api.command.ExecCreateCmdResponse;
import com.github.dockerjava.api.command.InspectContainerResponse;
import com.github.dockerjava.api.model.HostConfig;
import com.github.dockerjava.api.model.Statistics;
import com.github.dockerjava.core.DefaultDockerClientConfig;
import com.github.dockerjava.core.DockerClientBuilder;
import com.github.dockerjava.core.DockerClientConfig;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DockerStatsServiceImpl implements DockerStatsService {

    @Override
    public String getStats() {
        DockerClientConfig config = DefaultDockerClientConfig.createDefaultConfigBuilder().build();
        DockerClient docker = DockerClientBuilder.getInstance(config).build();

        String containerName = "mybusybox";
        CreateContainerResponse container = docker.createContainerCmd("busybox").withCmd("sleep", "9999")
                                                  .withName(containerName).withHostConfig(HostConfig.newHostConfig().withMemory(100000000L)).exec();
        System.out.println("Created container " + container.toString());

        docker.startContainerCmd(container.getId()).exec();

        ExecCreateCmdResponse exec = docker.execCreateCmd(container.getId()).withAttachStdout(true).withAttachStderr(true).withCmd("/bin/bash").exec();

        System.out.println("Created exec " + exec.toString());

        try {
            final SingleStatCallback statsCallback = docker.statsCmd(container.getId()).exec(new SingleStatCallback());
            final Optional<Statistics> latestStatsOptional = statsCallback.getLatestStatsWithTimeout(3);
            final Statistics latestStats = latestStatsOptional.orElseThrow(() -> new IllegalStateException("No Stats received"));
            System.out.println("Memory limit (stats):" + latestStats.getMemoryStats().getLimit());

            final InspectContainerResponse exec1 = docker.inspectContainerCmd(container.getId()).exec();
            System.out.println("Memory limit:" + exec1.getHostConfig().getMemory());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            docker.removeContainerCmd(container.getId()).withForce(true).exec();
        }

        return "";
    }
}
