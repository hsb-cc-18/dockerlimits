package com.cloudcomputing.docker.limits.cli;

import com.cloudcomputing.docker.limits.ApplicationConfiguration;
import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.UpdateContainerCmd;
import com.github.dockerjava.core.DockerClientImpl;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

@ShellComponent
public class UpdateContainer {

    @ShellMethod(key = "update-container", value = "Update limits of a container")
    public void update(
            final String containerId,
            @ShellOption(defaultValue = ShellOption.NULL) final String cpuShares,     // default is 1024
            @ShellOption(defaultValue = ShellOption.NULL) final String memoryLimit,
            @ShellOption(defaultValue = ShellOption.NULL) final String blkIoWeight) { //default is 500

        ApplicationConfiguration applicationConfiguration = new ApplicationConfiguration();
        DockerClient dockerClient = applicationConfiguration.getDockerClient();
        UpdateContainerCmd updateContainerCmd =  dockerClient.updateContainerCmd(containerId);

        if(cpuShares != null) {
            updateContainerCmd = updateContainerCmd.withCpuShares(Integer.parseInt(cpuShares));
        }
        if(memoryLimit != null){
            updateContainerCmd = updateContainerCmd.withMemory(Long.parseLong(memoryLimit));
        }
        if(blkIoWeight != null){
            updateContainerCmd = updateContainerCmd.withBlkioWeight(Integer.parseInt(blkIoWeight));
        }

        updateContainerCmd.exec();
    }

}
