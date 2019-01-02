package com.cloudcomputing.docker.limits.services.users;

import com.cloudcomputing.docker.limits.model.userrole.Role;

public interface UserRoleService {
    String STUDENT = "student";
    String CZOELLER = "czoeller";
    String EMPLOYE = "employe";
    String PROF = "prof";

    Role getRoleForUsername(String username);
}
