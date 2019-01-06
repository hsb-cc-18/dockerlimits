package com.cloudcomputing.docker.limits.model.io;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.ImmutableMap;

import javax.annotation.Nullable;

import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.NONE;

@JsonAutoDetect(fieldVisibility = NONE, getterVisibility = NONE, setterVisibility = NONE)
@JsonIgnoreProperties(ignoreUnknown = true)
public class DockerCompose {

    private String hsbUsername;

    @JsonProperty("version")
    private String version;

    @Nullable
    @JsonProperty("services")
    public ImmutableMap<String, ServiceSpec> services;

    @JsonIgnore
    public String getHsbUsername() {
        return hsbUsername;
    }

    @JsonProperty("x-hsb-username")
    public void setHsbUsername(String hsbUsername) {
        this.hsbUsername = hsbUsername;
    }

    public String getVersion() {
        return version;
    }

    public ImmutableMap<String, ServiceSpec> getServices() {
        return services;
    }


}
