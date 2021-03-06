package com.cloudcomputing.docker.limits.services.stats;

import com.cloudcomputing.docker.limits.services.util.ContainerIT;
import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.model.Statistics;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.shell.jline.InteractiveShellApplicationRunner;
import org.springframework.shell.jline.ScriptShellApplicationRunner;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(properties = {
        InteractiveShellApplicationRunner.SPRING_SHELL_INTERACTIVE_ENABLED + "=false",
        ScriptShellApplicationRunner.SPRING_SHELL_SCRIPT_ENABLED + "=false"
})
public class SingleStatCallbackTest extends ContainerIT {

    @Autowired
    private DockerClient dockerClient;

    private SingleStatCallback singleStatCallback;

    @Before
    @Override
    public void setUp() {
        super.setUp();
        singleStatCallback = new SingleStatCallback();
        dockerClient.statsCmd(getContainerId()).exec(singleStatCallback);
    }

    @Test
    public void getLatestStatsWithTimeout() throws InterruptedException {
        final Optional<Statistics> latestStatsWithTimeout = singleStatCallback.getLatestStatsWithTimeout(3);
        Boolean gotStats = singleStatCallback.gotStats();

        assertThat(gotStats).isTrue();
        assertThat(latestStatsWithTimeout).isPresent();
    }

    @Test
    public void getNoLatestStatsWithTimeout() throws InterruptedException {
        final Optional<Statistics> latestStatsWithTimeout = singleStatCallback.getLatestStatsWithTimeout(0);
        Boolean gotStats = singleStatCallback.gotStats();

        assertThat(gotStats).isFalse();
        assertThat(latestStatsWithTimeout).isNotPresent();
    }
}