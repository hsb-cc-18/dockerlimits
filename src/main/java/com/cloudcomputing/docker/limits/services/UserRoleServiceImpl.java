package com.cloudcomputing.docker.limits.services;

import com.cloudcomputing.docker.limits.model.userrole.Role;
import org.springframework.stereotype.Service;

@Service
public class UserRoleServiceImpl implements UserRoleService {
    @Override
    public Role getRoleForUsername(String username) {
        Role role;
        switch (username) {
            case "czoeller":
                role = Role.HSB_STUDENT;
                break;
            case "mitarbeiter":
                role = Role.HSB_MEMBER;
                break;
            case "prof":
                role = Role.HSB_PROF;
                break;
            default:
                role = Role.HSB_STUDENT;
        }
        return role;
    }
}
