package com.cloudcomputing.docker.limits.services.util;


import org.junit.Before;
import org.junit.Rule;
import pl.domzal.junit.docker.rule.DockerRule;

public abstract class ContainerIT {

    private String containerId = null;

    @Rule
    public DockerRule container = DockerRule.builder().imageName("nginx:latest").build();

    @Before
    public void setUp() {
        containerId = container.getContainerId();
    }

    public String getContainerId() {
        if (containerId == null) {
            throw new IllegalStateException("containerId must be set at this point. " +
                    "Did you call setUp() in the Subclass of ContainerIT?");
        }
        return containerId;
    }
}
