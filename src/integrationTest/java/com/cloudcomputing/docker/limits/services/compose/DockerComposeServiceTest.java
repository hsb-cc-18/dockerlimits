package com.cloudcomputing.docker.limits.services.compose;

import com.cloudcomputing.docker.limits.services.tags.DockerLabelService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DockerComposeServiceTest {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private DockerComposeService dockerComposeService;

    @Autowired
    private DockerLabelService dockerLabelService;

    @Before
    public void setUp() throws IOException, InterruptedException {


    }

    @After
    public void tearDown() {

    }

    @Test
    public void testComposedContainerHasLabel() throws URISyntaxException {
        final File dockerComposeFile = new File(getClass().getResource("../../io/docker-compose-with-username-and-service-labeled.yml").toURI());
        dockerComposeService.startComposeFile(dockerComposeFile);
        assertThat(dockerLabelService.getContainers("czoeller")).hasSize(1);
    }

}