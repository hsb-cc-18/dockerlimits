package com.cloudcomputing.docker.limits.services.stats;

import com.cloudcomputing.docker.limits.services.util.ContainerIT;
import com.github.dockerjava.api.DockerClient;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
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
        assertThat(stats.mem_limit.longValue()).isEqualTo(100000000L);
    }

}