package com.cloudcomputing.docker.limits.services.tags;

import com.cloudcomputing.docker.limits.services.users.UserRoleService;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import pl.domzal.junit.docker.rule.DockerRule;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DockerLabelServiceTest {

    private static final String LABEL_USER_TESTVALUE = UserRoleService.STUDENT;
    private String containerId = "invalid";

    @Autowired
    private DockerLabelService dockerLabelService;

    @Rule
    public DockerRule container = DockerRule.builder().imageName("busybox:latest").addLabel(DockerLabelService.LABEL_USER_KEY, LABEL_USER_TESTVALUE).build();

    @Before
    public void setUp() {
       containerId = container.getContainerId();
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
        assertThat(containers).size().isGreaterThanOrEqualTo(1);
    }
}