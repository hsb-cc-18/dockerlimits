package com.cloudcomputing.docker.limits.services.compose;

import javax.annotation.Nonnull;
import java.io.File;

public interface DockerComposeService {
    /**
     * Start a docker-compose.yml.
     * @param dockerComposeFilePath the docker-compose.yml file
     */
    void startComposeFile(@Nonnull File dockerComposeFilePath);
}
