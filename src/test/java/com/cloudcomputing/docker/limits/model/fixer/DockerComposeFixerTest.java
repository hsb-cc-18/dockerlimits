package com.cloudcomputing.docker.limits.model.fixer;

import com.cloudcomputing.docker.limits.model.io.ConstraintViolationSetAssert;
import com.cloudcomputing.docker.limits.model.io.DockerCompose;
import com.cloudcomputing.docker.limits.model.io.ServiceSpec;
import com.cloudcomputing.docker.limits.model.validator.DockerComposeValidator;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.validation.ConstraintViolation;
import java.util.Set;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DockerComposeFixerTest {

    @Autowired
    private DockerComposeValidator dockerComposeValidator;

    @Autowired
    private DockerComposeFixer dockerComposeFixer;

    @Test
    public void fix() {
        final ServiceSpec nginx = new ServiceSpec();
        nginx.labels = ImmutableList.<String>builder().build();
        final ImmutableMap<String, ServiceSpec> services = ImmutableMap.<String, ServiceSpec>builder().put("nginx", nginx).build();

        DockerCompose dockerCompose = new DockerCompose("czoeller", "2.4", services);
        Set<ConstraintViolation<DockerCompose>> violations = dockerComposeValidator.validate(dockerCompose);
        ConstraintViolationSetAssert.assertThat(violations).hasViolationOnPath("services[nginx].labels");

        // After it's fixed it has no violations
        final DockerCompose fixed = dockerComposeFixer.fix(violations, dockerCompose);
        violations = dockerComposeValidator.validate(fixed);
        ConstraintViolationSetAssert.assertThat(violations).hasNoViolations();
        assertThat(fixed.services.get("nginx").labels).contains("hsb.username=czoeller");
    }
}