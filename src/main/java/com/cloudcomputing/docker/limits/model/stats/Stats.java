package com.cloudcomputing.docker.limits.model.stats;


import de.xn__ho_hia.storage_unit.Megabyte;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import javax.annotation.Nonnull;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Stats {

    private Integer cpu_shares;
    private Megabyte mem_limit;

    public Stats(String mem_limit, Integer cpu_shares) {
        this.mem_limit = Megabyte.valueOf(toBytes(mem_limit));
        this.cpu_shares = cpu_shares;
    }

    public Stats add(@Nonnull Stats stats) {
        int cpu_percent = this.cpu_shares + stats.cpu_shares;
        return new Stats(this.mem_limit.add(stats.mem_limit).toString(), cpu_percent);
    }

    public Megabyte getMem_limit() {
        return mem_limit;
    }

    public Integer getCpu_shares() {
        return cpu_shares;
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
            //TODO: also process digits after ,
            String number = matcher.group(1);
            int pow = powerMap.get(matcher.group(2).toUpperCase());
            bytes = new BigDecimal(number);
            bytes = bytes.multiply(BigDecimal.valueOf(1000).pow(pow));
        } else {
            bytes = new BigDecimal(filesize);
        }

        returnValue = bytes.longValue();
        return returnValue;
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
