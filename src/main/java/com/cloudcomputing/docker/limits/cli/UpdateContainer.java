package com.cloudcomputing.docker.limits.cli;

import com.cloudcomputing.docker.limits.model.stats.Stats;
import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.UpdateContainerCmd;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

@ShellComponent
public class UpdateContainer {

    private final DockerClient dockerClient;

    @Autowired
    public UpdateContainer(DockerClient dockerClient) {
        this.dockerClient = dockerClient;
    }

    @ShellMethod(key = "update-container", value = "Update limits of a container")
    public void update(
            final String containerId,
            @ShellOption(defaultValue = ShellOption.NULL) final String cpuShares,     // default is 1024
            @ShellOption(defaultValue = ShellOption.NULL) final String memoryLimit,
            @ShellOption(defaultValue = ShellOption.NULL) final String blkIoWeight) { //default is 500

        UpdateContainerCmd updateContainerCmd = dockerClient.updateContainerCmd(containerId);

        if(cpuShares != null) {
            updateContainerCmd = updateContainerCmd.withCpuShares(Integer.parseInt(cpuShares));
        }
        if(memoryLimit != null){
            final long bytes = Stats.toBytes(memoryLimit);
            updateContainerCmd = updateContainerCmd.withMemory(bytes);
            updateContainerCmd = updateContainerCmd.withMemorySwap(bytes + 100);
        }
        if(blkIoWeight != null){
            updateContainerCmd = updateContainerCmd.withBlkioWeight(Integer.parseInt(blkIoWeight));
        }

        updateContainerCmd.exec();
    }

}
