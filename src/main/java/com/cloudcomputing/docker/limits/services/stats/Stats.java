package com.cloudcomputing.docker.limits.services.stats;


import de.xn__ho_hia.storage_unit.Mebibyte;

import javax.annotation.Nonnull;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Stats {
    public Double cpus;
    public Integer cpu_count, cpu_shares, cpu_percent,  cpu_quota;

    public Mebibyte mem_limit;

    public String cpu_period, cpuset,
            memswap_limit, mem_swappiness, mem_reservation, shm_size;

    public Stats(String mem_limit, Integer cpu_percent) {
        this.mem_limit = Mebibyte.valueOf(toBytes(mem_limit));
        this.cpu_percent = cpu_percent;
    }

    public Stats add(@Nonnull Stats stats) {
        int cpu_percent = this.cpu_percent + stats.cpu_percent;
        return new Stats(this.mem_limit.add(stats.mem_limit).toString(), cpu_percent);
    }

    public Long getMem_limit() {
        return mem_limit.longValue();
    }

    // https://stackoverflow.com/questions/12090598/parsing-human-readable-filesizes-in-java-to-bytes
    public static long toBytes(String filesize) {
        long returnValue = -1;
        Pattern patt = Pattern.compile("([\\d]+)[,]?+[\\d]*+.*?([GMK]+)", Pattern.CASE_INSENSITIVE);
        Matcher matcher = patt.matcher(filesize);
        Map<String, Integer> powerMap = new HashMap<>();
        powerMap.put("G", 3);
        powerMap.put("GB", 3);
        powerMap.put("M", 2);
        powerMap.put("MB", 2);
        powerMap.put("K", 1);
        powerMap.put("KB", 1);

        BigDecimal bytes;
        if (matcher.find()) {
            String number = matcher.group(1);
            int pow = powerMap.get(matcher.group(2).toUpperCase());
            bytes = new BigDecimal(number);
            bytes = bytes.multiply(BigDecimal.valueOf(1024).pow(pow));
        } else {
            bytes = new BigDecimal(filesize);
        }

        returnValue = bytes.longValue();
        return returnValue;
    }
}
