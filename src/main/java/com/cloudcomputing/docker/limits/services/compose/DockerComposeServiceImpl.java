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
