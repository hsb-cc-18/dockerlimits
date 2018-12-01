package com.cloudcomputing.docker.limits.model.io;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.dockerjava.api.model.RestartPolicy;
import com.google.common.collect.ImmutableMap;

import javax.annotation.Nullable;

import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.ANY;
import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.NONE;

@JsonAutoDetect(fieldVisibility = ANY, getterVisibility = NONE, setterVisibility = NONE)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Deploy {

    @Nullable
    @JsonProperty(value = "mode", defaultValue = "replicated")
    public String mode;

    @Nullable
    @JsonProperty(value = "replicas")
    public Integer replicas;

    @Nullable
    @JsonProperty("resources")
    public ResourceSpec resources;

    @Nullable
    @JsonProperty("restart_policy")
    public RestartPolicy restartPolicy;

    @Nullable
    @JsonProperty("labels")
    public ImmutableMap<String, String> labels;

}