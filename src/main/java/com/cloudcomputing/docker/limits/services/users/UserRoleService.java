package com.cloudcomputing.docker.limits.services.users;

import com.cloudcomputing.docker.limits.model.userrole.Role;

import javax.annotation.Nonnull;

public interface UserRoleService {
    String STUDENT = "student";
    String CZOELLER = "czoeller";
    String EMPLOYE = "employe";
    String PROF = "prof";

    Role getRoleForUsername(@Nonnull String username);
}
