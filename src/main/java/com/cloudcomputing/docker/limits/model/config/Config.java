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
    public String getMem_limit(String role);

    /**
     * gets cpu percentage for specifeid role
     * @param role
     * @return
     */
    public int getCpu_shares(String role);

    /**
     * gets blk weight for specifeid role
     * @param role
     * @return
     */
    public int getBlk_weight(String role);

    /**
     * sets memory limit for specified role
     * @param role
     * @param mem_limit
     */
    public void setMem_limit(String role, String mem_limit);

    /**
     * sets cpu percentage for specified role
     * @param role
     * @param cpu_shares
     */
    public void setCpu_shares(String role, int cpu_shares);

    /**
     * sets blk_weight for specified role
     * @param role
     * @param blk_weight
     */
    public void setBlk_weight(String role, int blk_weight);


}
