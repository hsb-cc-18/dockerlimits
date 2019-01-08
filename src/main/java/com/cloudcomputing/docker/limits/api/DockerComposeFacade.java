package com.cloudcomputing.docker.limits.api;

import com.cloudcomputing.docker.limits.io.DockerComposeReader;
import com.cloudcomputing.docker.limits.io.DockerComposeWriter;
import com.cloudcomputing.docker.limits.model.io.DockerCompose;
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

    @Autowired
    public DockerComposeFacade(DockerComposeReader dockerComposeReader, DockerComposeWriter dockerComposeWriter, DockerComposeService dockerComposeService) {
        this.dockerComposeReader = dockerComposeReader;
        this.dockerComposeWriter = dockerComposeWriter;
        this.dockerComposeService = dockerComposeService;
    }

    public void startDockerComposeFile(File dockerComposeFile) {
        try {
            final DockerCompose dockerCompose = readDockerCompose(dockerComposeFile);
            final File usersLatestDockerCompose = new File(FileUtils.getTempDirectory(), dockerCompose.getHsbUsername());
            //TODO: validate labels
            writeAndStartUsersDockerCompose(dockerCompose, usersLatestDockerCompose);
        } catch (IOException e) {
            logger.error("IO Failure", e);
        }
    }

    private void writeAndStartUsersDockerCompose(DockerCompose dockerCompose, File usersLatestDockerCompose) throws IOException {
        dockerComposeWriter.write(usersLatestDockerCompose, dockerCompose);
        dockerComposeService.startComposeFile(usersLatestDockerCompose);
    }

    private DockerCompose readDockerCompose(File dockerComposeFile) throws IOException {
        final InputStream dockerComposeYML = FileUtils.openInputStream(dockerComposeFile);
        return dockerComposeReader.read(dockerComposeYML);
    }


}
