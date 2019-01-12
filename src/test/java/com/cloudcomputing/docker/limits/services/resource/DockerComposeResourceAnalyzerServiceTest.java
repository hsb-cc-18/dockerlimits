package com.cloudcomputing.docker.limits.services.resource;

import com.cloudcomputing.docker.limits.model.io.DockerCompose;
import com.cloudcomputing.docker.limits.model.io.ServiceSpec;
import com.cloudcomputing.docker.limits.services.stats.Stats;
import com.google.common.collect.ImmutableMap;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static com.cloudcomputing.docker.limits.services.resource.DockerComposeResourceAnalyzerServiceImpl.COULD_NOT_SUM_MEM_OF_DOCKER_COMPOSE;
import static de.xn__ho_hia.storage_unit.StorageUnits.gigabyte;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.mockito.Mockito.when;

@SpringBootTest
@RunWith(SpringRunner.class)
public class DockerComposeResourceAnalyzerServiceTest {

    @Mock
    DockerCompose dockerCompose;

    @Autowired
    DockerComposeResourceAnalyzerService dockerComposeResourceAnalyzerService;

    @Test
    public void sumResources() {
        final ServiceSpec nginx = new ServiceSpec();
        nginx.mem_limit = "10G";
        nginx.cpu_percent = 20;
        final ServiceSpec busybox = new ServiceSpec();
        busybox.mem_limit = "6G";
        busybox.cpu_percent = 60;
        final ImmutableMap<String, ServiceSpec> services = ImmutableMap.<String, ServiceSpec>builder().put("nginx", nginx)
                                                                                                      .put("busybox", busybox)
                                                                                                      .build();
        when(dockerCompose.getServices()).thenReturn(services);

        final Stats stats = dockerComposeResourceAnalyzerService.sumResources(dockerCompose);

        assertThat(stats.mem_limit).isEqualTo(gigabyte(16));
        assertThat(stats.cpu_percent).isEqualTo(80);
    }

    @Test
    public void testException() {
        final ServiceSpec nginx = new ServiceSpec();
        // Set null explicitly
        nginx.mem_limit = null;

        final ImmutableMap<String, ServiceSpec> services = ImmutableMap.<String, ServiceSpec>builder().put("nginx", nginx).build();
        when(dockerCompose.getServices()).thenReturn(services);

        assertThatCode(() -> dockerComposeResourceAnalyzerService.sumResources(dockerCompose)).hasMessage(COULD_NOT_SUM_MEM_OF_DOCKER_COMPOSE);
    }
}