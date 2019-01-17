package com.cloudcomputing.docker.limits.services.users;

import com.cloudcomputing.docker.limits.model.userrole.Role;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.shell.jline.InteractiveShellApplicationRunner;
import org.springframework.shell.jline.ScriptShellApplicationRunner;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(properties = {
        InteractiveShellApplicationRunner.SPRING_SHELL_INTERACTIVE_ENABLED + "=false",
        ScriptShellApplicationRunner.SPRING_SHELL_SCRIPT_ENABLED + "=false"
})
public class UserRoleServiceTest {

    @Autowired
    UserRoleService userRoleService;

    @Test
    public void getRoleForUsername() {
        assertThat(userRoleService.getRoleForUsername(UserRoleService.CZOELLER)).isEqualTo(Role.HSB_STUDENT);
        assertThat(userRoleService.getRoleForUsername(UserRoleService.EMPLOYE)).isEqualTo(Role.HSB_MEMBER);
        assertThat(userRoleService.getRoleForUsername(UserRoleService.PROF)).isEqualTo(Role.HSB_PROF);
    }

    @Test
    public void testDefaultRole() {
        assertThat(userRoleService.getRoleForUsername("unknwown")).isEqualTo(Role.HSB_STUDENT);
    }
}