package com.cloudcomputing.docker.limits.api;

import com.cloudcomputing.docker.limits.io.DockerComposeReader;
import com.cloudcomputing.docker.limits.io.DockerComposeWriter;
import com.cloudcomputing.docker.limits.model.io.DockerCompose;
import com.cloudcomputing.docker.limits.model.io.DockerComposeValidator;
import com.cloudcomputing.docker.limits.services.compose.DockerComposeService;
import com.github.rozidan.springboot.logger.Loggable;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

@Service
@Loggable
public class DockerComposeFacade {

    private Logger logger = LoggerFactory.getLogger(getClass());
    private final DockerComposeReader dockerComposeReader;
    private final DockerComposeWriter dockerComposeWriter;
    private final DockerComposeService dockerComposeService;
    private final DockerComposeValidator dockerComposeValidator;

    @Autowired
    public DockerComposeFacade(DockerComposeReader dockerComposeReader, DockerComposeWriter dockerComposeWriter, DockerComposeService dockerComposeService, DockerComposeValidator dockerComposeValidator) {
        this.dockerComposeReader = dockerComposeReader;
        this.dockerComposeWriter = dockerComposeWriter;
        this.dockerComposeService = dockerComposeService;
        this.dockerComposeValidator = dockerComposeValidator;
    }

    public void startDockerComposeFile(File dockerComposeFile) {
        try {
            final InputStream dockerComposeYML = FileUtils.openInputStream(dockerComposeFile);
            final DockerCompose dockerCompose = dockerComposeReader.read(dockerComposeYML);
            final File usersLatestDockerCompose = new File(FileUtils.getTempDirectory(), dockerCompose.getHsbUsername());
            //TODO: validate labels
            dockerComposeValidator.validate(dockerCompose);
            dockerComposeWriter.write(usersLatestDockerCompose, dockerCompose);
            dockerComposeService.startComposeFile(usersLatestDockerCompose);
        } catch (IOException e) {
            logger.error("IO Failure", e);
        }
    }


}
