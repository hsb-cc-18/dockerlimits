package com.cloudcomputing.docker.limits.model;

import com.cloudcomputing.docker.limits.model.config.Config;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
@RunWith(SpringRunner.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class ConfigTest {


    @Autowired
    Config config;

    @Before
    public void setUp() throws IOException {
    }

    @Test
    public void testMemLimitProperties() throws IOException {
       assertThat(this.config.getMem_limit()).isEqualTo(1024);
    }

    @Test
    public void testCpuPercentProperties() throws IOException {
        assertThat(this.config.getCpu_percent()).isEqualTo(20);
    }

    @Test
    public void testMemLimitGetter() throws IOException {
        int mem_limit = 512;
        this.config.setMem_limit(mem_limit);
        assertThat(this.config.getMem_limit()).isEqualTo(mem_limit);
    }

    @Test
    public void testCpuPercentGetter() throws IOException {
        int cpu_percent = 25;
        this.config.setCpu_percent(cpu_percent);
        assertThat(this.config.getCpu_percent()).isEqualTo(cpu_percent);
    }
}