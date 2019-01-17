package com.cloudcomputing.docker.limits.model;

import com.cloudcomputing.docker.limits.model.config.Config;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.shell.jline.InteractiveShellApplicationRunner;
import org.springframework.shell.jline.ScriptShellApplicationRunner;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;


@RunWith(SpringRunner.class)
@SpringBootTest(properties = {
        InteractiveShellApplicationRunner.SPRING_SHELL_INTERACTIVE_ENABLED + "=false",
        ScriptShellApplicationRunner.SPRING_SHELL_SCRIPT_ENABLED + "=false"
})
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class ConfigTest {


    @Autowired
    Config config;

    @Before
    public void setUp() throws IOException {
        this.config.load();
    }

    @Test
    public void testMemLimitProperties() throws IOException {
       assertThat(this.config.getMem_limit("HSB-STUDENT")).isEqualTo("1G");
    }

    @Test
    public void testCpuPercentProperties() throws IOException {
        assertThat(this.config.getCpu_percent("HSB-STUDENT")).isEqualTo(20);
    }

    @Test
    public void testMemLimitGetter() throws IOException {
        String mem_limit = "512K";
        this.config.setMem_limit("HSB-STUDENT",mem_limit);
        assertThat(this.config.getMem_limit("HSB-STUDENT")).isEqualTo(mem_limit);
    }

    @Test
    public void testCpuPercentGetter() throws IOException {
        int cpu_percent = 25;
        this.config.setCpu_percent("HSB-STUDENT",cpu_percent);
        assertThat(this.config.getCpu_percent("HSB-STUDENT")).isEqualTo(cpu_percent);
    }

    @Test
    public void testSaveConfig() throws IOException {
        //new values
        String role = "HSB-STUDENT";
        int cpu_percent = 77;
        String mem_limit = "77";

        //keep original values
        int cpu_percent_tmp = this.config.getCpu_percent(role);
        String mem_limit_tmp = this.config.getMem_limit(role);

        //set new values and save them in config
        this.config.setCpu_percent(role,cpu_percent);
        this.config.setMem_limit(role,mem_limit);
        this.config.save();
        //set config to any other values
        this.config.setCpu_percent(role,0);
        this.config.setMem_limit(role,"0");
        //load config with new values
        this.config.load();
        //check loaded values euals new values
        assertThat(this.config.getCpu_percent(role)).isEqualTo(cpu_percent);
        assertThat(this.config.getMem_limit(role)).isEqualTo(mem_limit);
        //set all values back to original and save them
        this.config.setCpu_percent(role,cpu_percent_tmp);
        this.config.setMem_limit(role,mem_limit_tmp);
        this.config.save();

    }
}