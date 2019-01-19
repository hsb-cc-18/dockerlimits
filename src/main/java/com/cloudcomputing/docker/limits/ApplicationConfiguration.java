package com.cloudcomputing.docker.limits;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.core.DefaultDockerClientConfig;
import com.github.dockerjava.core.DockerClientBuilder;
import com.github.dockerjava.core.DockerClientConfig;
import org.jline.utils.AttributedString;
import org.jline.utils.AttributedStyle;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.shell.jline.PromptProvider;

@Configuration
public class ApplicationConfiguration {

    @Bean
    @Scope("singleton")
    @Lazy
    public DockerClient getDockerClient() {
        DockerClientConfig config = DefaultDockerClientConfig.createDefaultConfigBuilder().build();
        DockerClient dockerClient = DockerClientBuilder.getInstance(config).build();
        return dockerClient;
    }

    @Bean
    public PromptProvider myPromptProvider() {
        return () -> new AttributedString("dockerlimits:>", AttributedStyle.DEFAULT.foreground(AttributedStyle.RED));
    }
}
