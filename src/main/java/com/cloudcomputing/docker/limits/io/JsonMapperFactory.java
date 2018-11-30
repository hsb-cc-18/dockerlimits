package com.cloudcomputing.docker.limits.io;

import com.cloudcomputing.docker.limits.model.io.DockerCompose;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.fasterxml.jackson.datatype.guava.GuavaModule;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class JsonMapperFactory {

    @Bean
    public static ObjectMapper getObjectMapper() {
        return new ObjectMapper(new YAMLFactory());
    }

    @Bean
    public static ObjectReader getObjectReader(final ObjectMapper mapper) {
        final ObjectMapperBuilder objectMapperBuilder = new ObjectMapperBuilder();
        final ObjectMapper build = objectMapperBuilder.configure(mapper)
                                                      .withModule(new GuavaModule())
                                                      .build();
        return build.reader().forType(DockerCompose.class);
    }
}
