package com.cloudcomputing.docker.limits.cli;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

@ShellComponent
public class UpdateContainer {

    @ShellMethod(key = "update-container", value = "Update limits of a container")
    public void update(
            final String containerId,
            @ShellOption(defaultValue = ShellOption.NULL) final String memoryLimit,
            @ShellOption(defaultValue = ShellOption.NULL) final String cpuPercentage,
            @ShellOption(defaultValue = ShellOption.NULL) final String bandwith) {

        if(null != memoryLimit) {
            
        }

    }

}
