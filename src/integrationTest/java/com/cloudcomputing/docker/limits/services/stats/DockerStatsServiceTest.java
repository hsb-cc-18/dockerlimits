package com.cloudcomputing.docker.limits.services.stats;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DockerStatsServiceTest {

    @Autowired
    private DockerStatsService dockerStatsService;

    @Test
    public void getStats() {
        //TODO: replace with test behaviour
        final String stats = dockerStatsService.getStats();
        assertThat(stats).contains("memory=100000000");
    }
}