package com.cloudcomputing.docker.limits.services.resource;

import com.cloudcomputing.docker.limits.services.label.DockerLabelService;
import com.cloudcomputing.docker.limits.services.stats.DockerStatsService;
import com.cloudcomputing.docker.limits.services.stats.Stats;
import com.google.common.collect.Lists;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static de.xn__ho_hia.storage_unit.StorageUnits.megabyte;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ResourceUsageServiceImplTest {

    @MockBean
    DockerStatsService dockerStatsService;

    @MockBean
    DockerLabelService dockerLabelService;

    @Autowired
    ResourceUsageService resourceUsageService;

    @Test
    public void testSummarizesResources() {
        List<String> containerIds = Lists.newArrayList("28281c", "asdas2");
        when(dockerLabelService.getContainers("czoeller")).thenReturn(containerIds);
        when(dockerStatsService.getStats(any())).thenReturn(new Stats("2M", 30))
                                                .thenReturn(new Stats("8M", 50))
                                                .thenThrow(new IllegalStateException("No more data"));

        final Stats stats = resourceUsageService.sumResourceUsage("czoeller");
        assertThat(stats.mem_limit).isEqualTo(megabyte(10));
        assertThat(stats.cpu_percent).isEqualTo(80);
    }

}