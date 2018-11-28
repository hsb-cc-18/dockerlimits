package com.cloudcomputing.docker.limits.io;


import com.fasterxml.jackson.databind.ObjectReader;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;

@Component
public class DockerComposeReaderImpl implements DockerComposeReader {
    private ObjectReader objectReader;

    public DockerComposeReaderImpl() {
        objectReader = Jackson.objectReader();
    }

    @Override
    public DockerComposeFile read(InputStream inputStream) throws IOException {
        return objectReader.readValue(inputStream);
    }
}
