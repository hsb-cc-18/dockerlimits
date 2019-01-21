package com.cloudcomputing.docker.limits.cli;

import com.cloudcomputing.docker.limits.model.config.Config;
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
                       @ShellOption(defaultValue = ShellOption.NULL) final Integer cpuShares,
                       @ShellOption(defaultValue = ShellOption.NULL) final Integer blkio_weight){
        if (null != memoryLimit) {
            config.setMem_limit(role, memoryLimit);
        }
        if (null != cpuShares) {
            config.setCpu_shares(role, cpuShares);
        }
        if (null != blkio_weight) {
            config.setBlkio_weight(role, blkio_weight);
        }
        try {
            config.save();
            String message = String.format("New Config saved for role '%s': Memory Limit: %s, CPU Percentage: %d, Blkio Weight: %d%n", role, config.getMem_limit(role), config.getCpu_shares(role), config.getBlkio_weight(role));
            System.out.print(message);
            logger.info(message);
        } catch (IOException e) {
            logger.error("Could not save config: ", e);
            System.out.println("Could not save config");
        }
    }
}
