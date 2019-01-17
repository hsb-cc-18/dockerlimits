package com.cloudcomputing.docker.limits.cli;

import com.cloudcomputing.docker.limits.model.config.Config;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

@ShellComponent
public class UpdateConfig {

    Config config;
    @ShellMethod(key = "update-config", value = "Update config for a role")
    public void update(final String role,
                       @ShellOption(defaultValue = ShellOption.NULL) final String memoryLimit,
                       @ShellOption(defaultValue = ShellOption.NULL) final String cpuPercentage){
        if(null!=memoryLimit){
            config.setMem_limit(memoryLimit,role);
        }
        if(null!=cpuPercentage){
            config.setMem_limit(cpuPercentage,role);
        }
    }

}
