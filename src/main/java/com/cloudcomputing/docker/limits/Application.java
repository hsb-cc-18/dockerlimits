package com.cloudcomputing.docker.limits;

import com.cloudcomputing.docker.limits.api.DockerComposeFacade;
import org.jline.utils.AttributedString;
import org.jline.utils.AttributedStyle;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.context.PropertyPlaceholderAutoConfiguration;
import org.springframework.boot.autoconfigure.http.HttpMessageConvertersAutoConfiguration;
import org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration;
import org.springframework.boot.autoconfigure.thymeleaf.ThymeleafAutoConfiguration;
import org.springframework.boot.autoconfigure.web.servlet.DispatcherServletAutoConfiguration;
import org.springframework.boot.autoconfigure.web.servlet.HttpEncodingAutoConfiguration;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.shell.jline.PromptProvider;

import javax.annotation.Nonnull;
import java.io.File;

@SpringBootApplication(exclude = {
        DispatcherServletAutoConfiguration.class,
        ErrorMvcAutoConfiguration.class,
        HttpEncodingAutoConfiguration.class,
        HttpMessageConvertersAutoConfiguration.class,
        JacksonAutoConfiguration.class,
        PropertyPlaceholderAutoConfiguration.class,
        ThymeleafAutoConfiguration.class,
        WebMvcAutoConfiguration.class,
})
public class Application implements CommandLineRunner {

    private final File dockerComposeFile = new File(getClass().getResource("io/docker-compose-with-username-and-service-labeled.yml").getFile());

    private DockerComposeFacade dockerComposeFacade;

    public Application(@Nonnull DockerComposeFacade dockerComposeFacade) {
        this.dockerComposeFacade = dockerComposeFacade;

    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public PromptProvider myPromptProvider() {
        return () -> new AttributedString("dockerlimits:>", AttributedStyle.DEFAULT.foreground(AttributedStyle.RED));
    }

    /**
     * Callback used to run the bean.
     *
     * @param args incoming main method arguments
     * @throws Exception on error
     */
    @Override
    public void run(String... args) throws Exception {
        if (args.length > 0 && args[0].equals("run")) {
            dockerComposeFacade.startDockerComposeFile(dockerComposeFile);
        }
    }
}
