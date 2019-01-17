package com.cloudcomputing.docker.limits.services.compose;

import com.cloudcomputing.docker.limits.services.label.DockerLabelService;
import com.palantir.docker.compose.DockerComposeRule;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.shell.jline.InteractiveShellApplicationRunner;
import org.springframework.shell.jline.ScriptShellApplicationRunner;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.net.URISyntaxException;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(properties = {
        InteractiveShellApplicationRunner.SPRING_SHELL_INTERACTIVE_ENABLED + "=false",
        ScriptShellApplicationRunner.SPRING_SHELL_SCRIPT_ENABLED + "=false"
})
public class DockerComposeServiceTest {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private DockerComposeService dockerComposeService;

    @Autowired
    private DockerLabelService dockerLabelService;

    private DockerComposeRule dockerComposeRule;

    @Before
    public void setUp() throws URISyntaxException {
        final File dockerComposeFile = new File(getClass().getResource("../../io/docker-compose-with-username-and-service-labeled.yml").toURI());
        dockerComposeRule = dockerComposeService.startComposeFile(dockerComposeFile);
    }

    @After
    public void tearDown() {
        dockerComposeRule.after();
    }

    @Test
    public void testComposedContainerHasLabel() {
        assertThat(dockerLabelService.getContainers("czoeller")).hasSize(1);
    }

}