package com.cloudcomputing.docker.limits.services.label;

import javax.annotation.Nonnull;
import java.util.List;

public interface DockerLabelService {
    /** key used of the label that contains owners username */
    String LABEL_USER_KEY = "hsb.username";

    static String buildLabel(String username) {
        return String.format("%s=%s", LABEL_USER_KEY, username);
    }
    /**
     * Get the username of a container by containerId
     * @param containerId to query
     * @return the owners username
     */
    String getUsername(@Nonnull String containerId);

    /**
     * Get containers owned by a specific username
     * @param username the username of the owner
     * @return the containerIds owner by the user
     */
    List<String> getContainers(@Nonnull String username);
}
