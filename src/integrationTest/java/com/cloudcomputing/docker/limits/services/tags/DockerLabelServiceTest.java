package com.cloudcomputing.docker.limits.services.tags;

import com.cloudcomputing.docker.limits.services.users.UserRoleService;
import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.CreateContainerCmd;
import com.github.dockerjava.api.command.CreateContainerResponse;
import com.github.dockerjava.api.command.ExecCreateCmdResponse;
import com.github.dockerjava.api.exception.ConflictException;
import com.github.dockerjava.api.exception.NotFoundException;
import com.google.common.collect.ImmutableMap;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DockerLabelServiceTest {

    private static final String LABEL_USER_TESTVALUE = UserRoleService.STUDENT;
    private static final Map<String, String> TEST_LABELS = ImmutableMap.of(DockerLabelService.LABEL_USER_KEY, LABEL_USER_TESTVALUE);
    private String containerId = "invalid";

    @Autowired
    private DockerClient dockerClient;

    @Autowired
    private DockerLabelService dockerLabelService;

    @Before
    public void setUp() {
        String containerName = "mybusybox";

        try(CreateContainerCmd createContainerCmd = dockerClient.createContainerCmd("busybox").withCmd("sleep", "9999")
                                                       .withName(containerName)
                                                       .withLabels(TEST_LABELS)) {
            final CreateContainerResponse container = createContainerCmd.exec();

            System.out.println("Created container " + container.toString());

            dockerClient.startContainerCmd(container.getId()).exec();
            containerId = container.getId();

            ExecCreateCmdResponse exec = dockerClient.execCreateCmd(container.getId()).withAttachStdout(true).withAttachStderr(true).withCmd("/bin/bash").exec();

            System.out.println("Created exec " + exec.toString());
        } catch (NotFoundException | ConflictException e) {
            //TODO: rethrow service exception
            System.out.println(e);
        }
    }

    @After
    public void tearDown() {
        dockerClient.removeContainerCmd(containerId).withForce(true).exec();
    }

    @Test
    public void testInvalidContainerId() {
        assertThatCode(() -> dockerLabelService.getUsername("invalid")).hasMessageContaining("No Result");
    }

    @Test
    public void testGetUserLabel() {
        final String userLabel = dockerLabelService.getUsername(containerId);
        assertThat(userLabel).isEqualTo(LABEL_USER_TESTVALUE);
    }

    @Test
    public void getContainersByUserLabel() {
        final List<String> containers = dockerLabelService.getContainers(UserRoleService.STUDENT);
        assertThat(containers).hasSize(1);
    }
}