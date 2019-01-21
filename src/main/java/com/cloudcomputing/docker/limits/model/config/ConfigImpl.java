package com.cloudcomputing.docker.limits.model.config;

import com.cloudcomputing.docker.limits.io.FileManager;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.rozidan.springboot.logger.Loggable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;

/**
 *
 */
@Component
@Loggable
public class ConfigImpl implements Config{
    private final ObjectMapper mapper; // jackson databind
    private File configFile;
    private ConfigJson config;
    private final FileManager fileManager;


    @Autowired
    public ConfigImpl(ObjectMapper mapper, FileManager fileManager) throws IOException {
        this.mapper = mapper;
        this.fileManager = fileManager;
        this.load();
    }

    /**
     * loads configuration from file
     * @throws IOException
     */
    public void load() throws IOException{
        this.configFile = fileManager.getConfigFile();
        this.config = mapper.readValue(configFile, ConfigJson.class);
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
    public int getCpu_shares(String role){
        if (this.config.getResourceLimits().containsKey(role))
            return this.config.getResourceLimits().get(role).cpu_shares;
        else
            throw new IllegalArgumentException("Role \"" + role + "\" is not existing");
    }

    /**
     * gets blk weight for specifeid role
     * @param role role of user (student etc.)
     * @return relative blk weight
     */
    public int getBlkio_weight(String role){
        if (this.config.getResourceLimits().containsKey(role))
            return this.config.getResourceLimits().get(role).blkio_weight;
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
     * @param cpu_shares cpu usage in percent
     */
    public void setCpu_shares(String role, int cpu_shares) {
        if(cpu_shares<0 || cpu_shares >1024)
            throw new IllegalArgumentException("CPU percantage must be between 0 and 100!");
        if(this.config.getResourceLimits().containsKey(role)) //will check if a particular key exist or not
        {
            this.config.getResourceLimits().get(role).cpu_shares = cpu_shares;// increment the value by 1 to an already existing key
        }
        else
            throw new IllegalArgumentException("Role \"" + role + "\" is not existing");
    }

    /**
     * sets blk weight for specified role
     * @param role role of user (student etc.)
     * @param blkio_weight relative blk weight
     */
    public void setBlkio_weight(String role, int blkio_weight) {
        if(blkio_weight<10 || blkio_weight>1000)
            throw new IllegalArgumentException("Blkio weight must be between 10 and 1000!");
        if(this.config.getResourceLimits().containsKey(role)) //will check if a particular key exist or not
        {
            this.config.getResourceLimits().get(role).blkio_weight = blkio_weight;// increment the value by 1 to an already existing key
        }
        else
            throw new IllegalArgumentException("Role \"" + role + "\" is not existing");
    }
}
