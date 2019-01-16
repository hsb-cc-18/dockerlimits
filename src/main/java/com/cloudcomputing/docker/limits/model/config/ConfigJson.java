package com.cloudcomputing.docker.limits.model.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.NONE;

@JsonAutoDetect(fieldVisibility = NONE, getterVisibility = NONE, setterVisibility = NONE)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ConfigJson {
    @JsonProperty("mem_limit")
    private int mem_limit;

    @JsonProperty("cpu_percent")
    private int cpu_percent;


    public int getCpu_percent() {
        return cpu_percent;
    }

    public void setCpu_percent(int cpu_percent) {
        this.cpu_percent = cpu_percent;
    }

    public int getMem_limit() {
        return mem_limit;
    }

    public void setMem_limit(int mem_limit) {
        this.mem_limit = mem_limit;
    }

}
