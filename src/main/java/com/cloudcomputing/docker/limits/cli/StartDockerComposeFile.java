package com.cloudcomputing.docker.limits.cli;

import com.cloudcomputing.docker.limits.api.DockerComposeFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import java.io.File;

@ShellComponent
class StartDockerComposeFile {

    private static final String EXAMPLE_DOCKER_COMPOSE_FILE_PATH = "src/main/resources/com/cloudcomputing/docker/limits/io/docker-compose-with-username-and-service-labeled.yml";
    private final DockerComposeFacade dockerComposeFacade;

    @Autowired
    StartDockerComposeFile(DockerComposeFacade dockerComposeFacade) {
        this.dockerComposeFacade = dockerComposeFacade;
    }

    @ShellMethod(value = "Start a docker-compose file.")
    public void start(@ShellOption(help = "relative path to docker-compose file") final String dockerComposeFilePath) {
        final File dockerComposeFile = new File(".", dockerComposeFilePath);
        validateFile(dockerComposeFile);
        dockerComposeFacade.startDockerComposeFile(dockerComposeFile);
    }

    @ShellMethod(value = "Start predefined docker-compose file (for demo)")
    public void quickstart() {
        final File dockerComposeFile = new File(".", EXAMPLE_DOCKER_COMPOSE_FILE_PATH);
        validateFile(dockerComposeFile);
        dockerComposeFacade.startDockerComposeFile(dockerComposeFile);
    }

    private void validateFile(File dockerComposeFile) {
        if (!dockerComposeFile.exists()) {
            throw new IllegalArgumentException("File doesn't exist");
        } else if (dockerComposeFile.isDirectory()) {
            throw new IllegalArgumentException("Argument is not a file");
        }
    }
}
