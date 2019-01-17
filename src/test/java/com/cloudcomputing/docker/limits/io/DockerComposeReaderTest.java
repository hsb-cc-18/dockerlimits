package com.cloudcomputing.docker.limits.io;

import com.cloudcomputing.docker.limits.model.io.DockerCompose;
import com.cloudcomputing.docker.limits.model.io.ServiceSpec;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.shell.jline.InteractiveShellApplicationRunner;
import org.springframework.shell.jline.ScriptShellApplicationRunner;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(properties = {
        InteractiveShellApplicationRunner.SPRING_SHELL_INTERACTIVE_ENABLED + "=false",
        ScriptShellApplicationRunner.SPRING_SHELL_SCRIPT_ENABLED + "=false"
})
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
        assertThat(dockerCompose.getVersion()).isEqualTo("2.2");
        assertThat(dockerCompose.getServices()).isNotEmpty();
        assertThat(dockerCompose.getServices()).containsKey("web");
        final ServiceSpec serviceWeb = Objects.requireNonNull(dockerCompose.getServices()).get("web");
        assertThat(serviceWeb.mem_limit).isEqualTo("50M");
        assertThat(serviceWeb.cpu_percent).isEqualTo(50);
    }
}