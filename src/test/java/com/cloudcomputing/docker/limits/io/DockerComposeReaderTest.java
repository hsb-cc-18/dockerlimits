package com.cloudcomputing.docker.limits.io;

import com.cloudcomputing.docker.limits.model.io.DockerCompose;
import com.cloudcomputing.docker.limits.model.io.ServiceSpec;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

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

    @Test
    public void readResources() throws IOException {
        final InputStream dockerComposeYML = getClass().getResource("docker-compose-with-username-and-service.yml").openStream();
        final DockerCompose dockerCompose = dockerComposeReader.read(dockerComposeYML);
        assertThat(dockerCompose.getServices()).isNotEmpty();
        assertThat(dockerCompose.getServices()).containsKey("web");
        final ServiceSpec serviceWeb = Objects.requireNonNull(dockerCompose.getServices()).get("web");
        assertThat(serviceWeb.deploy.resources.limits.memory).isEqualTo("50M");
        assertThat(serviceWeb.deploy.resources.limits.cpus).isEqualTo(0.5);
    }
}