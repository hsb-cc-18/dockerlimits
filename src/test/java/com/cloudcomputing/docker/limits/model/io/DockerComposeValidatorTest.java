package com.cloudcomputing.docker.limits.model.io;

import com.cloudcomputing.docker.limits.model.validator.DockerComposeValidator;
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

        final Set<ConstraintViolation<DockerCompose>> violations = dockerComposeValidator.validate(dockerCompose);

        ConstraintViolationSetAssert.assertThat(violations).hasNoViolations();
    }

    @Test
    public void testWithNoVersion() {
        when(dockerCompose.getVersion()).thenReturn(null);

        final Set<ConstraintViolation<DockerCompose>> violations = dockerComposeValidator.validate(dockerCompose);

        ConstraintViolationSetAssert.assertThat(violations).hasViolationOnPath("version");
    }

    @Test
    public void testLabeledService() {
        final ServiceSpec nginx = new ServiceSpec();
        nginx.labels = ImmutableList.of(DockerLabelService.LABEL_USER_KEY + "=" +  UserRoleService.STUDENT);
        final ImmutableMap<String, ServiceSpec> services = ImmutableMap.<String, ServiceSpec>builder().put("nginx", nginx).build();

        when(dockerCompose.getVersion()).thenReturn("2.4");
        when(dockerCompose.getHsbUsername()).thenReturn("czoeller");

        // Test with label
        when(dockerCompose.getServices()).thenReturn(services);

        Set<ConstraintViolation<DockerCompose>> violations = dockerComposeValidator.validate(dockerCompose);
        ConstraintViolationSetAssert.assertThat(violations).hasNoViolations();

        // Test without label
        services.get("nginx").labels = ImmutableList.<String>builder().build();
        when(dockerCompose.getServices()).thenReturn(services);
        violations = dockerComposeValidator.validate(dockerCompose);
        ConstraintViolationSetAssert.assertThat(violations).hasViolationOnPath("services[nginx].labels");
    }

}