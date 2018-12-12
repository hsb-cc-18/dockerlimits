package com.cloudcomputing.docker.limits.services.users;

import com.cloudcomputing.docker.limits.model.userrole.Role;
import com.github.rozidan.springboot.logger.Loggable;
import org.springframework.stereotype.Service;

@Service
@Loggable
class UserRoleServiceImpl implements UserRoleService {
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
