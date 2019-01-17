package com.cloudcomputing.docker.limits.cli;

import com.cloudcomputing.docker.limits.model.details.ContainerDetails;
import com.cloudcomputing.docker.limits.services.details.ContainerDetailsService;
import com.cloudcomputing.docker.limits.services.label.DockerLabelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import java.util.List;
import java.util.stream.Collectors;

@ShellComponent
public class ListContainers {

    private final DockerLabelService dockerLabelService;
    private final ContainerDetailsService containerDetailsService;

    @Autowired
    ListContainers(DockerLabelService dockerLabelService, ContainerDetailsService containerDetailsService) {
        this.dockerLabelService = dockerLabelService;
        this.containerDetailsService = containerDetailsService;
    }

    @ShellMethod(key = "list-containers", value = "List all containers by username")
    public void list(String username) {
        final List<String> details = dockerLabelService.getContainers(username)
                                                       .parallelStream()
                                                       .map(containerDetailsService::getContainerDetails)
                                                       .map(ContainerDetails::toString)
                                                       .collect(Collectors.toList());

        if(details.isEmpty()) {
            System.out.println("No containers found");
        } else {
            details.forEach(System.out::println);
        }

    }

}
