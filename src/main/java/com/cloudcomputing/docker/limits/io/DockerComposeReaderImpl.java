package com.cloudcomputing.docker.limits.io;


import com.cloudcomputing.docker.limits.model.io.DockerCompose;
import com.fasterxml.jackson.databind.ObjectReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;

@Component
public class DockerComposeReaderImpl implements DockerComposeReader {
    private ObjectReader objectReader;

    @Autowired
    public DockerComposeReaderImpl(final ObjectReader objectReader) {
        this.objectReader = objectReader;
    }

    @Override
    public DockerCompose read(InputStream inputStream) throws IOException {
        return objectReader.readValue(inputStream);
    }
}
