package com.cloudcomputing.docker.limits.services.stats;

import com.cloudcomputing.docker.limits.model.stats.ResourceDescriptor;
import com.cloudcomputing.docker.limits.services.util.ContainerIT;
import com.github.dockerjava.api.DockerClient;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.shell.jline.InteractiveShellApplicationRunner;
import org.springframework.shell.jline.ScriptShellApplicationRunner;
import org.springframework.test.context.junit4.SpringRunner;

import static de.xn__ho_hia.storage_unit.StorageUnits.megabyte;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.data.Percentage.withPercentage;

@RunWith(SpringRunner.class)
@SpringBootTest(properties = {
        InteractiveShellApplicationRunner.SPRING_SHELL_INTERACTIVE_ENABLED + "=false",
        ScriptShellApplicationRunner.SPRING_SHELL_SCRIPT_ENABLED + "=false"
})
public class DockerStatsServiceTest extends ContainerIT {

    @Autowired
    private DockerClient dockerClient;

    @Autowired
    private DockerStatsService dockerStatsService;

    @Override
    @Before
    public void setUp() {
        super.setUp();
        // Set some limits
        dockerClient.updateContainerCmd(getContainerId())
                    .withMemorySwap(megabyte(200).longValue())
                    .withMemory(megabyte(100).longValue())
                    .exec();
    }

    @Test
    public void getStats() {
        final ResourceDescriptor resourceDescriptor = dockerStatsService.getStats(getContainerId());
        assertThat(resourceDescriptor.getMem_limit().longValue()).isCloseTo(100000000L, withPercentage(1));
    }

    @Test
    public void getConfig() {
        final ResourceDescriptor resourceDescriptor = dockerStatsService.getConfig(getContainerId());
        assertThat(resourceDescriptor.getMem_limit().longValue()).isEqualTo(100000000L);
    }

}