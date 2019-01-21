package com.cloudcomputing.docker.limits.services.limits;

import com.cloudcomputing.docker.limits.services.users.UserRoleService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.shell.jline.InteractiveShellApplicationRunner;
import org.springframework.shell.jline.ScriptShellApplicationRunner;
import org.springframework.test.context.junit4.SpringRunner;

import static de.xn__ho_hia.storage_unit.StorageUnits.gigabyte;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(properties = {
        InteractiveShellApplicationRunner.SPRING_SHELL_INTERACTIVE_ENABLED + "=false",
        ScriptShellApplicationRunner.SPRING_SHELL_SCRIPT_ENABLED + "=false"
})
public class LimitsQueryServiceImplTest {

    @Autowired
    LimitsQueryService limitsQueryService;

    @Test
    public void getMemoryLimitFromUsername() {
        assertThat(limitsQueryService.getLimitsForUsername(UserRoleService.STUDENT).getMem_limit()).isEqualTo(gigabyte(1));
    }

    @Test
    public void getCpuSharesLimitFromUsername() {
        assertThat(limitsQueryService.getLimitsForUsername(UserRoleService.STUDENT).getCpu_shares()).isEqualTo(20);
    }

    @Test
    public void getBlkioWeightLimitFromUsername() {
        assertThat(limitsQueryService.getLimitsForUsername(UserRoleService.STUDENT).getBlkio_weight()).isEqualTo(500);
    }

    @Test
    public void testDefaultLimits() {
        assertThat(limitsQueryService.getLimitsForUsername("unknown").getMem_limit()).isEqualTo(gigabyte(1));
        assertThat(limitsQueryService.getLimitsForUsername("unknown").getCpu_shares()).isEqualTo(20);
        assertThat(limitsQueryService.getLimitsForUsername("unknown").getBlkio_weight()).isEqualTo(500);
    }
}