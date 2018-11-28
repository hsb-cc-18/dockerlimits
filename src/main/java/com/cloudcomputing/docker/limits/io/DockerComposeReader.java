package com.cloudcomputing.docker.limits.io;

import java.io.IOException;
import java.io.InputStream;

public interface DockerComposeReader {
    DockerComposeFile read(InputStream inputStream) throws IOException;
}
