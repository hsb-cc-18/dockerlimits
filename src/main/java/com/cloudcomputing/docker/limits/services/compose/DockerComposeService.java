package com.cloudcomputing.docker.limits.services.compose;

import com.palantir.docker.compose.DockerComposeRule;

import javax.annotation.Nonnull;
import java.io.File;

public interface DockerComposeService {
    /**
     * Start a docker-compose.yml.
     * @param dockerComposeFilePath the docker-compose.yml file
     */
    DockerComposeRule startComposeFile(@Nonnull File dockerComposeFilePath);
}
