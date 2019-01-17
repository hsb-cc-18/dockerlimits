package com.cloudcomputing.docker.limits.cli;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@ShellComponent
public class UpdateContainer {

    @ShellMethod(key = "update-memory", value = "Update memory limit of a container")
    public void updateMemory(String memoryLimit, String containerId) {
        System.out.println("TODO");
    }
}
