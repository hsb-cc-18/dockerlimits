package com.cloudcomputing.docker.limits.model.details;

import de.xn__ho_hia.storage_unit.StorageUnits;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class ContainerDetails {

    private final String id;
    private final String name;
    private final String created;
    private final String image;
    private final Long cpu_percent;
    private final Long memory_usage;
    private final Long memory_limit;

    public ContainerDetails(String id, String name, String created, String image, Long cpu_percent, Long memory_usage, Long memory_limit) {
        this.id = id;
        this.name = name;
        this.created = created;
        this.image = image;
        this.cpu_percent = cpu_percent;
        this.memory_usage = memory_usage;
        this.memory_limit = memory_limit;
    }

    public String getId() {
        return id;
    }
    public String getName() {
        return name;
    }

    public String getCreated() {
        return created;
    }

    public String getImage() {
        return image;
    }

    public Long getCpu_percent() {
        return cpu_percent;
    }

    public String getMemory_limit() {
        return StorageUnits.bytes(memory_limit).asMegabyte().toString();
    }

    public String getMemory_usage() {
        return StorageUnits.bytes(memory_usage).asMegabyte().toString();
    }

    public Double getMemory_usage_percent() {
        return memory_usage / ((double) memory_limit) * 100;
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }

    public Map<String, String> toMap() {
        Map<String, String> map = new HashMap<>();
        for(Method m : getClass().getDeclaredMethods())
        {
            if(m.getName().startsWith("get"))
            {
                String value = null;
                try {
                    value = String.valueOf(m.invoke(this, null));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
                map.put(m.getName().substring(3), value);
            }
        }
        return map;
    }
}
