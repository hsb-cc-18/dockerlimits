package com.cloudcomputing.docker.limits.services.resource;

import com.cloudcomputing.docker.limits.model.io.DockerCompose;
import com.cloudcomputing.docker.limits.services.stats.Stats;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ResourceAuthorizeServiceTest {

    @Autowired
    ResourceAuthorizeService resourceAuthorizeService;

    @MockBean
    ResourceUsageService resourceUsageService;

    @MockBean
    DockerComposeResourceAnalyzerService dockerComposeResourceAnalyzerService;

    @Mock
    private DockerCompose dockerCompose;

    @Test
    public void returnsTrueIfEnoughResources() {

        when(resourceUsageService.sumResourceUsage(any())).thenReturn(new Stats("1G", 50));
        when(dockerComposeResourceAnalyzerService.sumResources(any())).thenReturn(new Stats("200M", 10));

        final boolean check = resourceAuthorizeService.isAuthorized(dockerCompose);
        assertThat(check).isTrue();
    }

    @Test
    public void returnsFalseIfNotEnoughResources() {
        when(resourceUsageService.sumResourceUsage(any())).thenReturn(new Stats("1G", 50));
        when(dockerComposeResourceAnalyzerService.sumResources(any())).thenReturn(new Stats("2G", 100));

        final boolean check = resourceAuthorizeService.isAuthorized(dockerCompose);

        assertThat(check).isFalse();
    }
}