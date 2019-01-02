package com.cloudcomputing.docker.limits.services.users;

import com.cloudcomputing.docker.limits.model.userrole.Role;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class UserRoleServiceTest {

    @Autowired
    UserRoleService userRoleService;

    @Test
    public void getRoleForUsername() {
        Assertions.assertThat(userRoleService.getRoleForUsername(UserRoleService.CZOELLER)).isEqualTo(Role.HSB_STUDENT);
        Assertions.assertThat(userRoleService.getRoleForUsername(UserRoleService.EMPLOYE)).isEqualTo(Role.HSB_MEMBER);
        Assertions.assertThat(userRoleService.getRoleForUsername(UserRoleService.PROF)).isEqualTo(Role.HSB_PROF);
    }
}