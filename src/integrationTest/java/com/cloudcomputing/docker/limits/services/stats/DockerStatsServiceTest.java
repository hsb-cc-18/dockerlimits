package com.cloudcomputing.docker.limits.services.stats;

import com.cloudcomputing.docker.limits.model.stats.Stats;
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
                    .withMemorySwap(200000000L)
                    .withMemory(100000000L)
                    .exec();
    }

    @Test
    public void getStats() {
        final Stats stats = dockerStatsService.getStats(getContainerId());
        assertThat(stats.mem_limit.longValue()).isCloseTo(100000000L, withPercentage(1));
    }

    @Test
    public void getConfig() {
        final Stats stats = dockerStatsService.getConfig(getContainerId());
        assertThat(stats.mem_limit.longValue()).isEqualTo(100000000L);
    }

}