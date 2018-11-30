package com.cloudcomputing.docker.limits.io;

import com.cloudcomputing.docker.limits.model.io.DockerCompose;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.io.InputStream;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@RunWith(SpringRunner.class)
public class DockerComposeReaderTest {

    @Autowired
    DockerComposeReader dockerComposeReader;

    @Test
    public void read() throws IOException {
        final InputStream dockerComposeYML = getClass().getResource("docker-compose-with-username.yml").openStream();
        final DockerCompose dockerCompose = dockerComposeReader.read(dockerComposeYML);
        assertThat(dockerCompose.getHsbUsername()).isEqualTo("czoeller");
    }
}