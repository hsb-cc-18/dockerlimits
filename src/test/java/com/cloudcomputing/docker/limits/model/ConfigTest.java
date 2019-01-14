package com.cloudcomputing.docker.limits.model;

import com.cloudcomputing.docker.limits.io.DockerComposeReader;
import com.cloudcomputing.docker.limits.io.DockerComposeWriter;
import com.cloudcomputing.docker.limits.model.config.Config;
import com.cloudcomputing.docker.limits.model.io.DockerCompose;
import org.apache.commons.io.FileUtils;
import org.junit.*;
import org.junit.rules.TemporaryFolder;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.hamcrest.Matchers.not;


@SpringBootTest
@RunWith(SpringRunner.class)
public class ConfigTest {
    //@Autowired
    Config config ;//= new Config();


    @Before
    public void setUp() throws IOException {
        this.config = new Config();
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