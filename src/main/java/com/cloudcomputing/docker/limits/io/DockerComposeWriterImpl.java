package com.cloudcomputing.docker.limits.io;

import com.cloudcomputing.docker.limits.model.io.DockerCompose;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;

@Component
public class DockerComposeWriterImpl implements DockerComposeWriter {

    private ObjectWriter objectWriter;

    @Autowired
    public DockerComposeWriterImpl(final ObjectWriter objectWriter) {
        this.objectWriter = objectWriter;
    }

    @Override
    public void write(File target, DockerCompose dockerCompose) throws IOException {
        objectWriter.writeValue(target, dockerCompose);
    }
}
