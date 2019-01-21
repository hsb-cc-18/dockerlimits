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
    private String mem_limit;

    //@JsonProperty("cpu_shares")
    private int cpu_percent;

    //@JsonProperty("blk_weight")
    private int blk_weight;


    public int getCpu_percent() {
        return cpu_percent;
    }

    public void setCpu_percent(int cpu_percent) {
        this.cpu_percent = cpu_percent;
    }

    public int getBlk_weight() {
        return blk_weight;
    }

    public void setBlk_weight(int blk_weight) {
        this.blk_weight = blk_weight;
    }

    public String getMem_limit() {
        return mem_limit;
    }

    public void setMem_limit(String mem_limit) {
        this.mem_limit = mem_limit;
    }

}
