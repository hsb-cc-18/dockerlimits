package com.cloudcomputing.docker.limits.cli;

import com.cloudcomputing.docker.limits.model.details.ContainerDetails;
import com.cloudcomputing.docker.limits.services.details.ContainerDetailsService;
import com.cloudcomputing.docker.limits.services.label.DockerLabelService;
import de.vandermeer.asciitable.AsciiTable;
import de.vandermeer.skb.interfaces.transformers.textformat.TextAlignment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import java.util.List;
import java.util.Map;
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
        final List<Map<String,String>> details = dockerLabelService.getContainers(username)
                                                                   .parallelStream()
                                                                   .map(containerDetailsService::getContainerDetails)
                                                                   .map(ContainerDetails::toMap)
                                                                   .collect(Collectors.toList());

        if(details.isEmpty()) {
            System.out.println("No containers found");
        } else {
            final AsciiTable at = new AsciiTable();
            at.addRule();
            at.addRow(details.get(0).keySet()).setTextAlignment(TextAlignment.CENTER);
            at.addRule();
            details.forEach(d ->at.addRow(d.values()));
            at.addRule();
            System.out.println(at.render());
        }
    }

}