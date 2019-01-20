package com.cloudcomputing.docker.limits.cli;

import com.cloudcomputing.docker.limits.model.config.Config;
import com.cloudcomputing.docker.limits.model.config.ConfigImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import java.io.IOException;

@ShellComponent
public class UpdateConfig {
    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    Config config;

    @ShellMethod(key = "update-config", value = "Update config for a role like memory-limit, cpu-share etc.")
    public void update(final String role,
                       @ShellOption(defaultValue = ShellOption.NULL) final String memoryLimit,
                       @ShellOption(defaultValue = ShellOption.NULL) final String cpuPercentage){
        if(null!=memoryLimit){
            config.setMem_limit(role,memoryLimit);
        }
        if(null!=cpuPercentage){
            config.setMem_limit(role,cpuPercentage);
        }
        try {
            config.save();
            System.out.println("New Config saved for \""+role+"\": Memory Limit: " + config.getMem_limit(role) + "CPU Percentage: " + config.getCpu_shares(role));
            logger.info("Saved config: \""+role+"\": mem = " + config.getMem_limit(role) + ", CPU % = " + config.getCpu_shares(role));
        } catch (IOException e) {
            logger.error("Could not save config: ", e);
            System.out.println("Could not save config");
        }
    }
}
