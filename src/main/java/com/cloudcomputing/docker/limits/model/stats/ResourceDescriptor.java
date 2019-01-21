package com.cloudcomputing.docker.limits.model.stats;


import de.xn__ho_hia.storage_unit.Megabyte;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.annotation.Nonnull;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ResourceDescriptor {

    private Megabyte mem_limit;
    private Integer cpu_shares;
    private Integer blkio_weight;

    public ResourceDescriptor(String mem_limit, Integer cpu_shares, Integer blkio_weight) {
        this.mem_limit = Megabyte.valueOf(toBytes(mem_limit));
        this.cpu_shares = cpu_shares;
        this.blkio_weight = blkio_weight;
    }

    public static ResourceDescriptor initial() {
        return new ResourceDescriptor("0M", 0, 0);
    }

    public ResourceDescriptor add(@Nonnull ResourceDescriptor other) {
        int cpu_percent = this.cpu_shares + other.cpu_shares;
        int blkio_weight = this.blkio_weight + other.blkio_weight;
        return new ResourceDescriptor(this.mem_limit.add(other.mem_limit).toString(), cpu_percent, blkio_weight);
    }

    public Megabyte getMem_limit() {
        return mem_limit;
    }

    public Integer getCpu_shares() {
        return cpu_shares;
    }

    public Integer getBlkio_weight() {
        return blkio_weight;
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
        return ReflectionToStringBuilder.toString(this, ToStringStyle.NO_CLASS_NAME_STYLE);
    }
}
