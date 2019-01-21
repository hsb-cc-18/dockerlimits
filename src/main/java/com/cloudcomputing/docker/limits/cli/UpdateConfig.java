package com.cloudcomputing.docker.limits.cli;

import com.cloudcomputing.docker.limits.cli.provider.AvailableRolesProvider;
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

    private final Config config;

    @Autowired
    public UpdateConfig(Config config) {
        this.config = config;
    }

    @ShellMethod(key = "update-config", value = "Update config for a role like memory-limit, cpu-share etc.")
    public void update(@ShellOption(help = "role to update", valueProvider = AvailableRolesProvider.class) final String role,
                       @ShellOption(help = "CPU shares (relative weight)", defaultValue = ShellOption.NULL) final Integer cpuShares,
                       @ShellOption(help = "Memory limit", defaultValue = ShellOption.NULL) final String memoryLimit,
                       @ShellOption(help = "Block IO (relative weight), between 10 and 1000", defaultValue = ShellOption.NULL) final Integer blkIoWeight)
    {
        if (null != memoryLimit) {
            config.setMem_limit(role, memoryLimit);
        }
        if (null != cpuShares) {
            config.setCpu_shares(role, cpuShares);
        }
        if (null != blkIoWeight) {
            config.setBlkio_weight(role, blkIoWeight);
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
