package com.cloudcomputing.docker.limits.io;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.fasterxml.jackson.datatype.guava.GuavaModule;

final class Jackson {
    private static final ObjectMapper MAPPER = new ObjectMapper(new YAMLFactory());

    static {
        MAPPER.registerModule(new GuavaModule());
    }

    static ObjectReader objectReader() {
        return MAPPER.reader().forType(DockerComposeFile.class);
    }
}