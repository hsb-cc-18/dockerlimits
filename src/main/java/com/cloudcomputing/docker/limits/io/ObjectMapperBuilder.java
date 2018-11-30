package com.cloudcomputing.docker.limits.io;

import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ObjectMapperBuilder {
    private static ObjectMapper mapper;

    public ObjectMapperBuilder configure(final ObjectMapper mapper) {
        ObjectMapperBuilder.mapper = mapper;
        return this;
    }

    public ObjectMapperBuilder withModule(final Module module) {
        mapper.registerModule(module);
        return this;
    }

    public ObjectMapper build() {
        return mapper;
    }
}