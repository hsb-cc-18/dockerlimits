package com.cloudcomputing.docker.limits.model.config;

import com.fasterxml.jackson.databind.ObjectReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import java.io.*;

@Component
public class ConfigImpl {
    private ObjectReader objectReader;
    private  final File initialFile = new File("src/main/java/com/cloudcomputing/docker/limits/model/config/resources.yml");

    @Autowired
    public ConfigImpl(final ObjectReader objectReader) {
        this.objectReader = objectReader;
    }


    public void loadConfig() throws IOException{
        final InputStream configYML = new FileInputStream(this.initialFile);
        ConfigJson config = objectReader.readValue(configYML);

    }


    public int getMem_limit(String role){
     /*   if (this.config.getResourceLimits().containsKey(role))
            return this.config.getResourceLimits().get(role).mem_limit;
        else
            throw new IllegalArgumentException("Given role is not existing");*/
     return 1;
    }

    public Double getCpu_percent(String role){
      /*  if (this.config.getResourceLimits().containsKey(role))
            return this.config.getResourceLimits().get(role).cpu_percent;
        else
            throw new IllegalArgumentException("Given role is not existing");*/
      return 1.0;
    }

    public void setMem_limit(String role, int mem_limit){
        assert mem_limit >= 0;
       /* if(this.config.getResourceLimits().containsKey(role)) //will check if a particular key exist or not
        {
            this.config.getResourceLimits().get(role).mem_limit = mem_limit;// increment the value by 1 to an already existing key
        }
        else
            throw new IllegalArgumentException("Given role is not existing");*/
    }

    public void setCpu_percent(String role, Double cpu_percent) {
        assert cpu_percent >= 0;
        /*if(this.config.getResourceLimits().containsKey(role)) //will check if a particular key exist or not
        {
            this.config.getResourceLimits().get(role).cpu_percent = cpu_percent;// increment the value by 1 to an already existing key
        }
        else
            throw new IllegalArgumentException("Given role is not existing");*/
    }
}
