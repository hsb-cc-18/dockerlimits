package com.cloudcomputing.docker.limits;

import com.cloudcomputing.docker.limits.model.details.ContainerDetails;
import com.cloudcomputing.docker.limits.services.details.ContainerDetailsService;
import com.github.dockerjava.api.DockerClient;
import de.xn__ho_hia.storage_unit.StorageUnits;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.shell.jline.InteractiveShellApplicationRunner;
import org.springframework.shell.jline.ScriptShellApplicationRunner;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(properties = {
        InteractiveShellApplicationRunner.SPRING_SHELL_INTERACTIVE_ENABLED + "=false",
        ScriptShellApplicationRunner.SPRING_SHELL_SCRIPT_ENABLED + "=false"
})
public class T {

    @Autowired
    private DockerClient dockerClient;

    @Autowired
    private ContainerDetailsService containerDetailsService;


    @Test
    public void test() {
        dockerClient.updateContainerCmd("8856e592238be9c2607f2611672053127b71c995fc486feae19c2356ae551b32")
                    .withMemory(StorageUnits.megabyte(100).longValue())
                    .exec();
    }

    @Test
    public void test2() {
        final ContainerDetails containerDetails = containerDetailsService.getContainerDetails("8856e592238be9c2607f2611672053127b71c995fc486feae19c2356ae551b32");
        System.out.println();
    }
}
