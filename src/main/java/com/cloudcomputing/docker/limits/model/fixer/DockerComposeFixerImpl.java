package com.cloudcomputing.docker.limits.model.fixer;

import com.cloudcomputing.docker.limits.model.io.DockerCompose;
import com.cloudcomputing.docker.limits.model.io.ServiceSpec;
import com.cloudcomputing.docker.limits.services.label.DockerLabelService;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Sets;
import org.hibernate.validator.internal.engine.path.NodeImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Nonnull;
import javax.validation.ConstraintViolation;
import javax.validation.Path;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Service
public class DockerComposeFixerImpl implements DockerComposeFixer {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public DockerCompose fix(@Nonnull Set<ConstraintViolation<DockerCompose>> violations, @Nonnull DockerCompose dockerCompose) {
        for (ConstraintViolation<DockerCompose> violation : violations) {
            final Path propertyPath = violation.getPropertyPath();
            if(propertyPath.toString().contains("label")) {
                fixLabel(dockerCompose, propertyPath);
            }
        }
        return dockerCompose;
    }

    /**
     * The method replaces the {@link ServiceSpec#labels}.
     * @param dockerCompose dockerCompose reference (is modified by this method)
     * @param propertyPath the property where the violation occurred
     */
    private void fixLabel(@Nonnull final DockerCompose dockerCompose, @Nonnull final Path propertyPath) {
        for (Path.Node pathNode : propertyPath) {
            final NodeImpl node = (NodeImpl) pathNode;
            if (pathNode.getName().equals("labels")) {
                final ServiceSpec serviceSpec = (ServiceSpec) node.getParent().getValue();
                final String serviceName = (String) node.getKey();
                Objects.requireNonNull(serviceSpec, "ServiceSpec must be set to fix labels for the specific service.");
                Objects.requireNonNull(serviceName, "Service name must be set to fix labels for the specific service.");
                final String userLabel = DockerLabelService.buildLabel(dockerCompose.getHsbUsername());
                final List<String> existingLabels = serviceSpec.labels;
                final Set<String> accLabels = Sets.newHashSet();
                if(null == existingLabels) {
                    logger.debug("Service {} has no labels.", serviceName);
                } else {
                    accLabels.addAll(existingLabels);
                }
                accLabels.add(userLabel);
                logger.debug("Fixed: Added user label '{}' to service '{}'", userLabel, serviceName);
                serviceSpec.labels = ImmutableList.copyOf(accLabels);
            }
        }
    }
}
