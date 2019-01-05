package com.cloudcomputing.docker.limits.services.users;

import com.cloudcomputing.docker.limits.model.userrole.Role;
import com.github.rozidan.springboot.logger.Loggable;
import org.springframework.stereotype.Service;

import javax.annotation.Nonnull;

/**
 * Stub for UserManagement interface like LDAP.
 */
@Service
@Loggable
class UserRoleServiceImpl implements UserRoleService {

    @Override
    public Role getRoleForUsername(@Nonnull String username) {
        Role role;
        switch (username) {
            case STUDENT:
            case CZOELLER:
                role = Role.HSB_STUDENT;
                break;
            case EMPLOYE:
                role = Role.HSB_MEMBER;
                break;
            case PROF:
                role = Role.HSB_PROF;
                break;
            default:
                role = Role.HSB_STUDENT;
        }
        return role;
    }
}
