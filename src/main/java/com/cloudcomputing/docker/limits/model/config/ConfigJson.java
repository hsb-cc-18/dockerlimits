package com.cloudcomputing.docker.limits.model.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;


import java.util.Map;

import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.NONE;

@JsonAutoDetect(fieldVisibility = NONE, getterVisibility = NONE, setterVisibility = NONE)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ConfigJson {

    @JsonProperty("resourceLimits")
    public Map<String, ResourcesSpec> resourceLimits;

    public Map<String, ResourcesSpec> getResourceLimits() {
        return resourceLimits;
    }

    public void setResourceLimits(Map<String, ResourcesSpec> resourceLimits) {
        this.resourceLimits = resourceLimits;
    }

    //@JsonProperty("mem_limit")
    private int mem_limit = 10;

    //@JsonProperty("cpu_percent")
    private Double cpu_percent = 2.5;


    public Double getCpu_percent() {
        return cpu_percent;
    }

    public void setCpu_percent(Double cpu_percent) {
        this.cpu_percent = cpu_percent;
    }

    public int getMem_limit() {
        return mem_limit;
    }

    public void setMem_limit(int mem_limit) {
        this.mem_limit = mem_limit;
    }

}
