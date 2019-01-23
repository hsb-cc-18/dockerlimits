package com.cloudcomputing.docker.limits.services.resource;

import javax.annotation.Nonnull;
import java.util.List;

public class NotEnoughResourcesException extends RuntimeException {

    @Nonnull
    private final List<String> errors;

    public NotEnoughResourcesException(@Nonnull List<String> errors) {
        if( errors.isEmpty() ) {
            throw new IllegalArgumentException("No errors passed");
        }
        this.errors = errors;
    }

    @Override
    public String getMessage() {
        return String.format("Please reduce your requested resources by %s.",  String.join(", ", errors));
    }
}
