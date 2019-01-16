package com.cloudcomputing.docker.limits.model;

import com.cloudcomputing.docker.limits.model.config.ConfigImpl;
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
    ConfigImpl config;

    @Before
    public void setUp() throws IOException {
        this.config.loadConfig();
    }

    @Test
    public void testMemLimitProperties() throws IOException {
       assertThat(this.config.getMem_limit("student")).isEqualTo(1024);
    }

    @Test
    public void testCpuPercentProperties() throws IOException {
        assertThat(this.config.getCpu_percent("student")).isEqualTo(20);
    }

    @Test
    public void testMemLimitGetter() throws IOException {
        int mem_limit = 512;
        this.config.setMem_limit("student",mem_limit);
        assertThat(this.config.getMem_limit("student")).isEqualTo(mem_limit);
    }

    @Test
    public void testCpuPercentGetter() throws IOException {
        Double cpu_percent = 25.0;
        this.config.setCpu_percent("student",cpu_percent);
        assertThat(this.config.getCpu_percent("student")).isEqualTo(cpu_percent);
    }
}