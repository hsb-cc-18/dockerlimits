package com.cloudcomputing.docker.limits.model.config;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import java.io.*;

@Component
public class ConfigImpl {
    final ObjectMapper mapper = new ObjectMapper(new YAMLFactory()); // jackson databind
    private ObjectReader objectReader;
    private  final File configFile = new File("src/main/java/com/cloudcomputing/docker/limits/model/config/resources.yml");
    ConfigJson config;
    @Autowired
    public ConfigImpl(final ObjectReader objectReader) {
        this.objectReader = objectReader;
    }


    public void loadConfig() throws IOException{

        this.config =  mapper.readValue(configFile, ConfigJson.class);
    }

    public void saveConfig() throws IOException {
        //Convert object to JSON string and pretty print
        String jsonInString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(config);
        System.out.println(jsonInString);
        mapper.writeValue(this.configFile, config);
    }

    public int getMem_limit(String role){
       if (this.config.getResourceLimits().containsKey(role))
            return this.config.getResourceLimits().get(role).mem_limit;
        else
            throw new IllegalArgumentException("Given role is not existing");
     //return this.config.getMem_limit();
    }

    public Double getCpu_percent(String role){
        if (this.config.getResourceLimits().containsKey(role))
            return this.config.getResourceLimits().get(role).cpu_percent;
        else
            throw new IllegalArgumentException("Given role is not existing");
    }

    public void setMem_limit(String role, int mem_limit){
        assert mem_limit >= 0;
        if(this.config.getResourceLimits().containsKey(role)) //will check if a particular key exist or not
        {
            this.config.getResourceLimits().get(role).mem_limit = mem_limit;// increment the value by 1 to an already existing key
        }
        else
            throw new IllegalArgumentException("Given role is not existing");
    }

    public void setCpu_percent(String role, Double cpu_percent) {
        assert cpu_percent >= 0;
        if(this.config.getResourceLimits().containsKey(role)) //will check if a particular key exist or not
        {
            this.config.getResourceLimits().get(role).cpu_percent = cpu_percent;// increment the value by 1 to an already existing key
        }
        else
            throw new IllegalArgumentException("Given role is not existing");
    }
}
