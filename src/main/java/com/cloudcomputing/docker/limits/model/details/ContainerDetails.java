package com.cloudcomputing.docker.limits.model.details;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

public class ContainerDetails {

    private String name;
    private final String created;
    private String image;

    public ContainerDetails(String name, String created, String image) {
        this.name = name;
        this.created = created;
        this.image = image;
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

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
