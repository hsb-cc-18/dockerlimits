package com.cloudcomputing.docker.limits.model.config;

//import com.sun.istack.internal.NotNull;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Configuration
@EnableConfigurationProperties
@ConfigurationProperties(prefix="resources") // prefix resources, find resources.* values
public class Config {

    //@NotNull
    private int mem_limit;
    //@NotNull
    private int cpu_percent;

    public int getMem_limit(){
        return this.mem_limit;
    }
    public int getCpu_percent(){
        return this.cpu_percent;
    }

    public void setMem_limit(int mem_limit){
        this.mem_limit = mem_limit;
    }

    public void setCpu_percent(int cpu_percent) {
        this.cpu_percent = cpu_percent;
    }
}
