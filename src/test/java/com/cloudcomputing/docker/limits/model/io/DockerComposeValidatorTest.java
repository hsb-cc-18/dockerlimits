package com.cloudcomputing.docker.limits.model.io;

import com.cloudcomputing.docker.limits.services.label.DockerLabelService;
import com.cloudcomputing.docker.limits.services.users.UserRoleService;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.validation.ConstraintViolation;
import java.util.Set;

import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DockerComposeValidatorTest {

    @Mock
    private DockerCompose dockerCompose;

    @Autowired
    private DockerComposeValidator dockerComposeValidator;

    @Test
    public void validatePositive() {
        when(dockerCompose.getVersion()).thenReturn("2.4");
        when(dockerCompose.getHsbUsername()).thenReturn("czoeller");

        final Set<ConstraintViolation<DockerCompose>> errors = dockerComposeValidator.validate(dockerCompose);

        ConstraintViolationSetAssert.assertThat(errors).hasNoViolations();
    }

    @Test
    public void testWithNoVersion() {
        when(dockerCompose.getVersion()).thenReturn(null);

        final Set<ConstraintViolation<DockerCompose>> errors = dockerComposeValidator.validate(dockerCompose);

        ConstraintViolationSetAssert.assertThat(errors).hasViolationOnPath("version");
    }

    @Test
    public void testUnlabeledService() {
        final ServiceSpec nginx = new ServiceSpec();
        nginx.labels = ImmutableList.of(DockerLabelService.LABEL_USER_KEY, UserRoleService.STUDENT);
        final ImmutableMap<String, ServiceSpec> services = ImmutableMap.<String, ServiceSpec>builder().put("nginx", nginx).build();

        when(dockerCompose.getServices()).thenReturn(services);

        final Set<ConstraintViolation<DockerCompose>> errors = dockerComposeValidator.validate(dockerCompose);

        ConstraintViolationSetAssert.assertThat(errors).hasViolationOnPath("getVersion");
    }
}