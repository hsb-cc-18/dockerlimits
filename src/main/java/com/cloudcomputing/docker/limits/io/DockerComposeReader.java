package com.cloudcomputing.docker.limits.io;


import com.cloudcomputing.docker.limits.model.io.DockerCompose;

import java.io.IOException;
import java.io.InputStream;

public interface DockerComposeReader {
    DockerCompose read(InputStream inputStream) throws IOException;
}
