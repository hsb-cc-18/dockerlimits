package com.cloudcomputing.docker.limits.io;

import com.cloudcomputing.docker.limits.model.io.DockerCompose;

import java.io.File;
import java.io.IOException;

public interface DockerComposeWriter {
    void write(File target, DockerCompose dockerCompose) throws IOException;
}
