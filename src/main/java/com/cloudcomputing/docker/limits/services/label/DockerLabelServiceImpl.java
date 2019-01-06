package com.cloudcomputing.docker.limits.services.label;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.ListContainersCmd;
import com.github.dockerjava.api.exception.ConflictException;
import com.github.dockerjava.api.exception.NotFoundException;
import com.github.dockerjava.api.model.Container;
import com.github.rozidan.springboot.logger.Loggable;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Loggable
public class DockerLabelServiceImpl implements DockerLabelService {

    private DockerClient dockerClient;

    @Autowired
    public DockerLabelServiceImpl(DockerClient dockerClient) {
        this.dockerClient = dockerClient;
    }

    private List<Container> queryContainers(@Nonnull final ListContainersCmd listContainersCmd) {
        try {
           return listContainersCmd.exec();
        } catch (NotFoundException | ConflictException e) {
            System.out.println(e);
        }
        return Lists.newArrayList();
    }

    @Override
    public String getUsername(@Nonnull String containerId) {
        final ListContainersCmd listContainersCmd = dockerClient.listContainersCmd()
                                                                .withShowAll(true)
                                                                .withIdFilter(Lists.newArrayList(containerId));

        return queryContainers(listContainersCmd).stream()
                        .map(Container::getLabels)
                        .map(e -> e.get(DockerLabelService.LABEL_USER_KEY))
                        .findFirst()
                        .orElseThrow(() -> new IllegalStateException("No Result"));
    }

    @Override
    public List<String> getContainers(@Nonnull String username) {
        final ImmutableMap<String, String> labels = ImmutableMap.of(DockerLabelService.LABEL_USER_KEY, username);
        final ListContainersCmd listContainersCmd = dockerClient.listContainersCmd()
                                                                .withShowAll(true)
                                                                .withLabelFilter(labels);
        return queryContainers(listContainersCmd).stream().map(Container::getId).collect(Collectors.toList());
    }
}
