package com.cloudcomputing.docker.limits.model.config;

import com.cloudcomputing.docker.limits.io.FileManager;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import java.io.*;

/**
 *
 */
@Component
public class ConfigImpl implements Config{
    private final ObjectMapper mapper = new ObjectMapper(new YAMLFactory()); // jackson databind
    private ObjectReader objectReader;
    private  File configFile;// = new File("src/main/java/com/cloudcomputing/docker/limits/model/config/resources.yml");
    private ConfigJson config;
    private FileManager fileManager = new FileManager();


    @Autowired
    public ConfigImpl(final ObjectReader objectReader) throws IOException {
        this.objectReader = objectReader;
        this.load();
    }

    /**
     * loads configuration from file
     * @throws IOException
     */
    public void load() throws IOException{
        this.configFile = fileManager.getConfigFile();
        this.config =  mapper.readValue(configFile, ConfigJson.class);
    }

    /**
     * saves configuration into file
     * @throws IOException
     */
    public void save() throws IOException {
        mapper.writeValue(this.configFile, config);
    }

    /**
     * gets mempory limit for specified role
     * @param role role of user (student etc.)
     * @return maximum allowabale memory usage for role
     */
    public String getMem_limit(String role){
       if (this.config.getResourceLimits().containsKey(role))
            return this.config.getResourceLimits().get(role).mem_limit;
        else
            throw new IllegalArgumentException("Role \"" + role + "\" is not existing");
     //return this.config.getMem_limit();
    }

    /**
     * gets cpu percentage for specifeid role
     * @param role role of user (student etc.)
     * @return cpu usage in percent
     */
    public int getCpu_percent(String role){
        if (this.config.getResourceLimits().containsKey(role))
            return this.config.getResourceLimits().get(role).cpu_percent;
        else
            throw new IllegalArgumentException("Role \"" + role + "\" is not existing");
    }

    /**
     * sets memory limit for specified role
     * @param role role of user (student etc.)
     * @param mem_limit maximum allowabale memory usage for role
     */
    public void setMem_limit(String role, String mem_limit){
        if(this.config.getResourceLimits().containsKey(role)) //will check if a particular key exist or not
        {
            this.config.getResourceLimits().get(role).mem_limit = mem_limit;// increment the value by 1 to an already existing key
        }
        else
            throw new IllegalArgumentException("Role \"" + role + "\" is not existing");
    }

    /**
     * sets cpu percentage for specified role
     * @param role role of user (student etc.)
     * @param cpu_percent cpu usage in percent
     */
    public void setCpu_percent(String role, int cpu_percent) {
        if(cpu_percent<0 || cpu_percent >100)
            throw new IllegalArgumentException("CPU percantage must be between 0 and 100!");
        if(this.config.getResourceLimits().containsKey(role)) //will check if a particular key exist or not
        {
            this.config.getResourceLimits().get(role).cpu_percent = cpu_percent;// increment the value by 1 to an already existing key
        }
        else
            throw new IllegalArgumentException("Role \"" + role + "\" is not existing");
    }
}
