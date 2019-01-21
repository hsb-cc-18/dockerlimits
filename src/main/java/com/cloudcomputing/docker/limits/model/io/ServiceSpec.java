package com.cloudcomputing.docker.limits.model.io;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.google.common.collect.ImmutableList;

import javax.annotation.Nullable;

import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.ANY;
import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.NONE;

@JsonAutoDetect(fieldVisibility = ANY, getterVisibility = NONE, setterVisibility = NONE)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ServiceSpec {

    public String image;

    @Nullable
    public String container_name;

    @Nullable
    public ImmutableList<String> networks;

    @Nullable
    public ImmutableList<String> ports;

    @Nullable
    public ImmutableList<String> labels;

    @Nullable
    public BlkioConfigSpec blkio_config;

    @Nullable
    public Double cpus;

    @Nullable
    public Integer cpu_count, cpu_shares, cpu_percent,  cpu_quota;

    @Nullable
    public String mem_limit, cpu_period, cpuset, domainname,
            hostname, ipc, mac_address, memswap_limit, mem_swappiness, mem_reservation, oom_kill_disable, oom_score_adj,
            privileged, read_only, shm_size, stdin_open, tty, user, working_dir;
}