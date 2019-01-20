package com.cloudcomputing.docker.limits.model.fixer;

import com.cloudcomputing.docker.limits.model.io.ConstraintViolationSetAssert;
import com.cloudcomputing.docker.limits.model.io.DockerCompose;
import com.cloudcomputing.docker.limits.model.io.ServiceSpec;
import com.cloudcomputing.docker.limits.model.validator.DockerComposeValidator;
import com.cloudcomputing.docker.limits.services.label.DockerLabelService;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.shell.jline.InteractiveShellApplicationRunner;
import org.springframework.shell.jline.ScriptShellApplicationRunner;
import org.springframework.test.context.junit4.SpringRunner;

import javax.validation.ConstraintViolation;
import java.util.Set;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(properties = {
        InteractiveShellApplicationRunner.SPRING_SHELL_INTERACTIVE_ENABLED + "=false",
        ScriptShellApplicationRunner.SPRING_SHELL_SCRIPT_ENABLED + "=false"
})
public class DockerComposeFixerTest {

    @Autowired
    private DockerComposeValidator dockerComposeValidator;

    @Autowired
    private DockerComposeFixer dockerComposeFixer;

    @Test
    public void fixAlreadyFineDC() {
        DockerCompose dockerCompose = buildDockerCompose();
        dockerCompose.services.get("nginx").labels = ImmutableList.<String>builder().add(DockerLabelService.buildLabel("czoeller")).build();
        Set<ConstraintViolation<DockerCompose>> violations = dockerComposeValidator.validate(dockerCompose);
        ConstraintViolationSetAssert.assertThat(violations).hasNoViolations();

        // After it's fixed it has no violations and the label is set
        final DockerCompose fixed = dockerComposeFixer.fix(violations, dockerCompose);
        violations = dockerComposeValidator.validate(fixed);
        ConstraintViolationSetAssert.assertThat(violations).hasNoViolations();
        assertThat(fixed.services.get("nginx").labels).contains(DockerLabelService.buildLabel("czoeller"));
    }

    @Test
    public void fixDCWithEmptyLabels() {
        DockerCompose dockerCompose = buildDockerCompose();
        dockerCompose.services.get("nginx").labels = ImmutableList.of();
        Set<ConstraintViolation<DockerCompose>> violations = dockerComposeValidator.validate(dockerCompose);
        ConstraintViolationSetAssert.assertThat(violations).hasViolationOnPath("services[nginx].labels");

        // After it's fixed it has no violations and the label is set
        final DockerCompose fixed = dockerComposeFixer.fix(violations, dockerCompose);
        violations = dockerComposeValidator.validate(fixed);
        ConstraintViolationSetAssert.assertThat(violations).hasNoViolations();
        assertThat(fixed.services.get("nginx").labels).contains(DockerLabelService.buildLabel("czoeller"));
    }

    @Test
    public void fixDCWithNullLabels() {
        DockerCompose dockerCompose = buildDockerCompose();
        dockerCompose.services.get("nginx").labels = null; // explicit null
        Set<ConstraintViolation<DockerCompose>> violations = dockerComposeValidator.validate(dockerCompose);
        ConstraintViolationSetAssert.assertThat(violations).hasViolationOnPath("services[nginx].labels");

        // After it's fixed it has no violations and the label is set
        final DockerCompose fixed = dockerComposeFixer.fix(violations, dockerCompose);
        violations = dockerComposeValidator.validate(fixed);
        ConstraintViolationSetAssert.assertThat(violations).hasNoViolations();
        assertThat(fixed.services.get("nginx").labels).contains(DockerLabelService.buildLabel("czoeller"));
    }

    private static DockerCompose buildDockerCompose() {
        final ServiceSpec nginx = new ServiceSpec();
        final ImmutableMap<String, ServiceSpec> services = ImmutableMap.<String, ServiceSpec>builder().put("nginx", nginx).build();
        return new DockerCompose("czoeller", "2.4", services);
    }
}