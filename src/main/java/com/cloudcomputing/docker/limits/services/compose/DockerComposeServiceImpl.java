package com.cloudcomputing.docker.limits.services.compose;


import com.palantir.docker.compose.DockerComposeRule;
import com.palantir.docker.compose.configuration.ShutdownStrategy;
import com.palantir.docker.compose.connection.DockerMachine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Nonnull;
import java.io.File;

@Service
public class DockerComposeServiceImpl implements DockerComposeService {
    private Logger logger = LoggerFactory.getLogger(getClass());
    private final DockerMachine dockerMachine;

    public DockerComposeServiceImpl() {
        dockerMachine = DockerMachine.localMachine().build();
    }

    @Override
    public void startComposeFile(@Nonnull File dockerComposeFilePath) {
        DockerComposeRule dockerComposeRule;

        //TODO: 1 Read DockerCompose object with DockerComposeReader
        //TODO: 2 User hsbUsername from dockerCompose object and check that all services tagged with the username tag. Fix if required
        //TODO: 3 Write new DockerCompose file with DockerComposeWrite (newDockerComposeFile.yml)
        //TODO: 4 Path the File to the following Builder method .file(newDockerComposeFile.getAbsolutePath())

        try {
            dockerComposeRule = DockerComposeRule.builder()
                                                 .file(dockerComposeFilePath.getAbsolutePath())
                                                 .machine(dockerMachine)
                                                 .shutdownStrategy(ShutdownStrategy.SKIP)
                                                 .build();

            dockerComposeRule.before();
        } catch (Exception e) {
            logger.error("Exception", e);
        }
    }
}
