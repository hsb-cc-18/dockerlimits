package com.cloudcomputing.docker.limits.api;

import com.cloudcomputing.docker.limits.io.DockerComposeReader;
import com.cloudcomputing.docker.limits.io.DockerComposeWriter;
import com.cloudcomputing.docker.limits.services.compose.DockerComposeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
public class DockerComposeFacade {

    private final DockerComposeReader dockerComposeReader;
    private final DockerComposeWriter dockerComposeWriter;
    private final DockerComposeService dockerComposeService;

    @Autowired
    public DockerComposeFacade(DockerComposeReader dockerComposeReader, DockerComposeWriter dockerComposeWriter, DockerComposeService dockerComposeService) {
        this.dockerComposeService = dockerComposeService;
        this.dockerComposeReader = dockerComposeReader;
        this.dockerComposeWriter = dockerComposeWriter;
    }

    public void startDockerComposeFile(File dockerComposeFile) {
        //TODO: 1 Read DockerCompose object with dockerComposeReader
        //TODO: 2 Use hsbUsername from dockerCompose object and check that all services tagged with the username tag. Fix if required
        //TODO: 3 Write new DockerCompose file with dockerComposeWriter (newDockerComposeFile.yml)
        //TODO: 4 Pass the File to the dockerComposeService
    }

}
