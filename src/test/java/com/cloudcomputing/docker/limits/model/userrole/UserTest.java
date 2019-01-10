package com.cloudcomputing.docker.limits.model.userrole;

import com.cloudcomputing.docker.limits.services.users.UserRoleService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


@RunWith(SpringRunner.class)
public class UserTest {

    @Test
    public void testProperties() {
        User user = new User();
        user.username = UserRoleService.CZOELLER;
        user.userRole = Role.HSB_STUDENT;

        assertThat(user.username).isEqualTo(UserRoleService.CZOELLER);
        assertThat(user.userRole).isEqualTo(Role.HSB_STUDENT);
    }

}