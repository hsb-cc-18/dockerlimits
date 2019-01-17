package com.cloudcomputing.docker.limits.model.config;

import java.io.IOException;

public interface Config {
    void load() throws IOException;

    /**
     * saves configuration into file
     * @throws IOException
     */
    void save() throws IOException;

    /**
     * gets mempory limit for specified role
     * @param role
     * @return
     */
    public int getMem_limit(String role);

    /**
     * gets cpu percentage for specifeid role
     * @param role
     * @return
     */
    public Double getCpu_percent(String role);

    /**
     * sets memory limit for specified role
     * @param role
     * @param mem_limit
     */
    public void setMem_limit(String role, int mem_limit);

    /**
     * sets cpu percentage for specified role
     * @param role
     * @param cpu_percent
     */
    public void setCpu_percent(String role, Double cpu_percent);


}
