package com.cloudcomputing.docker.limits.services.details;

import com.cloudcomputing.docker.limits.model.details.ContainerDetails;
import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.InspectContainerResponse;
import com.github.rozidan.springboot.logger.Loggable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Loggable
public class ContainerDetailsServiceImpl implements ContainerDetailsService {

    private DockerClient dockerClient;

    @Autowired
    public ContainerDetailsServiceImpl(DockerClient dockerClient) {
        this.dockerClient = dockerClient;
    }

    @Override
    public ContainerDetails getContainerDetails(String containerId) {
        final InspectContainerResponse container = dockerClient.inspectContainerCmd(containerId).exec();
        return buildContainerDetails(container);
    }

    private ContainerDetails buildContainerDetails(InspectContainerResponse container) {
        final String name = container.getName();
        final String created = container.getCreated();
        final String image = container.getImageId();

        return new ContainerDetails(name, created, image);
    }

}
