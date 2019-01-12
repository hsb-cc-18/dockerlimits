package com.cloudcomputing.docker.limits.api;

import com.cloudcomputing.docker.limits.io.DockerComposeReader;
import com.cloudcomputing.docker.limits.io.DockerComposeWriter;
import com.cloudcomputing.docker.limits.model.fixer.DockerComposeFixer;
import com.cloudcomputing.docker.limits.model.io.DockerCompose;
import com.cloudcomputing.docker.limits.model.validator.DockerComposeValidator;
import com.cloudcomputing.docker.limits.services.compose.DockerComposeService;
import com.cloudcomputing.docker.limits.services.resource.ResourceAuthorizeService;
import com.github.rozidan.springboot.logger.Loggable;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Set;

@Service
@Loggable
public class DockerComposeFacade {

    private Logger logger = LoggerFactory.getLogger(getClass());
    private final DockerComposeReader dockerComposeReader;
    private final DockerComposeWriter dockerComposeWriter;
    private final DockerComposeService dockerComposeService;
    private final DockerComposeValidator dockerComposeValidator;
    private final DockerComposeFixer dockerComposeFixer;
    private final ResourceAuthorizeService resourceAuthorizeService;

    @Autowired
    public DockerComposeFacade(DockerComposeReader dockerComposeReader, DockerComposeWriter dockerComposeWriter, DockerComposeService dockerComposeService, DockerComposeValidator dockerComposeValidator, DockerComposeFixer dockerComposeFixer, ResourceAuthorizeService resourceAuthorizeService) {
        this.dockerComposeReader = dockerComposeReader;
        this.dockerComposeWriter = dockerComposeWriter;
        this.dockerComposeService = dockerComposeService;
        this.dockerComposeValidator = dockerComposeValidator;
        this.dockerComposeFixer = dockerComposeFixer;
        this.resourceAuthorizeService = resourceAuthorizeService;
    }

    public void startDockerComposeFile(File dockerComposeFile) {
        try {
            final InputStream dockerComposeYML = FileUtils.openInputStream(dockerComposeFile);
            final DockerCompose dockerCompose = dockerComposeReader.read(dockerComposeYML);
            final File usersLatestDockerComposeFile = new File(FileUtils.getTempDirectory(), dockerCompose.getHsbUsername());
            Set<ConstraintViolation<DockerCompose>> violations = dockerComposeValidator.validate(dockerCompose);
            final DockerCompose fixedDockerCompose = dockerComposeFixer.fix(violations, dockerCompose);
            violations = dockerComposeValidator.validate(dockerCompose);

            if(!violations.isEmpty()) {
                logger.debug("There are {} errors in your docker-compose we were not able to fix automatically. {}", violations.size(), violations);
                return;
            }

            if( resourceAuthorizeService.isAuthorized(dockerCompose) ) {
                dockerComposeWriter.write(usersLatestDockerComposeFile, fixedDockerCompose);
                dockerComposeService.startComposeFile(usersLatestDockerComposeFile);
            } else {
                logger.debug("Not enough resources for user request.");
            }
        } catch (IOException e) {
            logger.error("IO Failure", e);
        }
    }


}
