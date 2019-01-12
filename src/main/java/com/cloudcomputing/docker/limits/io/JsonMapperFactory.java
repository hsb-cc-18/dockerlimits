package com.cloudcomputing.docker.limits.io;

import com.cloudcomputing.docker.limits.model.io.DockerCompose;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.fasterxml.jackson.dataformat.yaml.YAMLGenerator;
import com.fasterxml.jackson.datatype.guava.GuavaModule;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class JsonMapperFactory {

    private JsonMapperFactory() {}

    @Bean
    public static ObjectMapper getObjectMapper() {
        final YAMLFactory yamlFactory = new YAMLFactory()
                .disable(YAMLGenerator.Feature.WRITE_DOC_START_MARKER);
        return new ObjectMapper(yamlFactory);
    }

    @Bean
    public static ObjectReader getObjectReader(final ObjectMapper mapper) {
        final ObjectMapperBuilder objectMapperBuilder = new ObjectMapperBuilder();
        final ObjectMapper build = objectMapperBuilder.configure(mapper)
                                                      .withModule(new GuavaModule())
                                                      .build();
        return build.reader().forType(DockerCompose.class);
    }

    @Bean
    public static ObjectWriter getObjectWriter(final ObjectMapper mapper) {
        final ObjectMapperBuilder objectMapperBuilder = new ObjectMapperBuilder();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
        final ObjectMapper build = objectMapperBuilder.configure(mapper)
                                                      .withModule(new GuavaModule())
                                                      .build();
        return build.writer()
                    .with(new DefaultPrettyPrinter())
                    .forType(DockerCompose.class);
    }
}
