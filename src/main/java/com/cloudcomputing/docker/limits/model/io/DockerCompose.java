package com.cloudcomputing.docker.limits.model.io;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.ANY;
import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.NONE;

@JsonAutoDetect(fieldVisibility = ANY, getterVisibility = NONE, setterVisibility = NONE)
@JsonIgnoreProperties(ignoreUnknown = true)
public class DockerCompose {

    @JsonProperty("x-hsb-username")
    private String hsbUsername;

    @JsonProperty("version")
    private String version;

    public String getHsbUsername() {
        return hsbUsername;
    }

    public String getVersion() {
        return version;
    }
}
