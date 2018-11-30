package com.cloudcomputing.docker.limits.services;

import com.cloudcomputing.docker.limits.model.userrole.Role;

public interface UserRoleService {
    Role getRoleForUsername(String username);
}
