package com.cloudcomputing.docker.limits.services.resource;

import com.cloudcomputing.docker.limits.model.io.DockerCompose;
import com.cloudcomputing.docker.limits.model.stats.Stats;
import com.cloudcomputing.docker.limits.services.limits.LimitsQueryService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.shell.jline.InteractiveShellApplicationRunner;
import org.springframework.shell.jline.ScriptShellApplicationRunner;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest(properties = {
        InteractiveShellApplicationRunner.SPRING_SHELL_INTERACTIVE_ENABLED + "=false",
        ScriptShellApplicationRunner.SPRING_SHELL_SCRIPT_ENABLED + "=false"
})
public class ResourceAuthorizeServiceTest {

    @Autowired
    ResourceAuthorizeService resourceAuthorizeService;

    @MockBean
    LimitsQueryService limitsQueryService;

    @MockBean
    ResourceUsageService resourceUsageService;

    @MockBean
    DockerComposeResourceAnalyzerService dockerComposeResourceAnalyzerService;

    @Mock
    private DockerCompose dockerCompose;

    @Test
    public void returnsTrueIfEnoughResources() {

        when(limitsQueryService.getLimitsForUsername(any())).thenReturn(new Stats("1G", 20));
        when(resourceUsageService.sumResourceUsage(any())).thenReturn(new Stats("500M", 10));
        when(dockerComposeResourceAnalyzerService.sumResources(any())).thenReturn(new Stats("200M", 9));

        final boolean check = resourceAuthorizeService.isAuthorized(dockerCompose);
        assertThat(check).isTrue();
    }

    @Test
    public void returnsFalseIfNotEnoughMemory() {
        when(limitsQueryService.getLimitsForUsername(any())).thenReturn(new Stats("1G", 20));
        when(resourceUsageService.sumResourceUsage(any())).thenReturn(new Stats("1G", 10));
        when(dockerComposeResourceAnalyzerService.sumResources(any())).thenReturn(new Stats("9G", 10));

        final boolean check = resourceAuthorizeService.isAuthorized(dockerCompose);

        assertThat(check).isFalse();
    }

    @Test
    public void returnsFalseIfNotEnoughCpuShares() {
        when(limitsQueryService.getLimitsForUsername(any())).thenReturn(new Stats("1G", 20));
        when(resourceUsageService.sumResourceUsage(any())).thenReturn(new Stats("1G", 10));
        when(dockerComposeResourceAnalyzerService.sumResources(any())).thenReturn(new Stats("2G", 100));

        final boolean check = resourceAuthorizeService.isAuthorized(dockerCompose);

        assertThat(check).isFalse();
    }
}