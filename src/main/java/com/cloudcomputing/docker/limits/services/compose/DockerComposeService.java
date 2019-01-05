package com.cloudcomputing.docker.limits.services.compose;

import java.io.File;

public interface DockerComposeService {
    /**
     * Start a docker-compose.yml.
     * @param dockerComposeFilePath the docker-compose.yml file
     */
    void startComposeFile(File dockerComposeFilePath);
}
