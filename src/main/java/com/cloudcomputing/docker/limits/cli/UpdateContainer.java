package com.cloudcomputing.docker.limits.cli;

import com.cloudcomputing.docker.limits.model.stats.ResourceDescriptor;
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

    @ShellMethod(key = "update-container", value = "Update the resource limits of a container")
    public void update(
            @ShellOption(help = "a container id") final String containerId,
            @ShellOption(help = "CPU shares (relative weight)", defaultValue = ShellOption.NULL) final Integer cpuShares,     // default is 1024
            @ShellOption(help = "Memory limit", defaultValue = ShellOption.NULL) final String memoryLimit,
            @ShellOption(help = "Block IO (relative weight), between 10 and 1000", defaultValue = ShellOption.NULL) final Integer blkIoWeight) //default is 500
    {

        UpdateContainerCmd updateContainerCmd = dockerClient.updateContainerCmd(containerId);

        if(cpuShares != null) {
            updateContainerCmd = updateContainerCmd.withCpuShares(cpuShares);
        }
        if(memoryLimit != null){
            final long bytes = ResourceDescriptor.toBytes(memoryLimit);
            updateContainerCmd = updateContainerCmd.withMemory(bytes);
            updateContainerCmd = updateContainerCmd.withMemorySwap(bytes + 100);
        }
        if(blkIoWeight != null){
            updateContainerCmd = updateContainerCmd.withBlkioWeight(blkIoWeight);
        }

        updateContainerCmd.exec();
    }

}
