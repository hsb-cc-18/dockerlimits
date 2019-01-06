package com.cloudcomputing.docker.limits.api;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DockerComposeFacadeTest {

    @Autowired
    DockerComposeFacade dockerComposeFacade;

    @Test
    public void startDockerComposeFile() {

        final File dockerComposeFile = new File(getClass().getResource("../io/docker-compose-with-username.yml").getFile());

        dockerComposeFacade.startDockerComposeFile(dockerComposeFile);
    }
}