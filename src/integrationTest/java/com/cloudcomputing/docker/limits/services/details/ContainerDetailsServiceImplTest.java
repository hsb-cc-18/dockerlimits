package com.cloudcomputing.docker.limits.services.details;

import com.cloudcomputing.docker.limits.model.details.ContainerDetails;
import com.cloudcomputing.docker.limits.services.util.ContainerIT;
import org.joda.time.DateTime;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.shell.jline.InteractiveShellApplicationRunner;
import org.springframework.shell.jline.ScriptShellApplicationRunner;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.jodatime.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(properties = {
        InteractiveShellApplicationRunner.SPRING_SHELL_INTERACTIVE_ENABLED + "=false",
        ScriptShellApplicationRunner.SPRING_SHELL_SCRIPT_ENABLED + "=false"
})
public class ContainerDetailsServiceImplTest extends ContainerIT {

    @Autowired
    ContainerDetailsService containerDetailsService;

    @Test
    public void getContainerDetails() {
        final ContainerDetails containerDetails = containerDetailsService.getContainerDetails(getContainerId());
        assertThat(containerDetails).isNotNull();
        assertThat(containerDetails.getName()).isEqualTo(container.getContainerInfo().name());
        assertThat(new DateTime(containerDetails.getCreated())).isEqualToIgnoringSeconds(new DateTime(
                container.getContainerInfo()
                         .created()));
    }
}