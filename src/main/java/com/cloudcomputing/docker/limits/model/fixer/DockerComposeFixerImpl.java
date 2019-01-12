package com.cloudcomputing.docker.limits.model.fixer;

import com.cloudcomputing.docker.limits.model.io.DockerCompose;
import com.cloudcomputing.docker.limits.model.io.ServiceSpec;
import com.cloudcomputing.docker.limits.services.label.DockerLabelService;
import com.google.common.collect.ImmutableList;
import org.hibernate.validator.internal.engine.path.NodeImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.Path;
import java.util.List;
import java.util.Set;

@Service
public class DockerComposeFixerImpl implements DockerComposeFixer {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public DockerCompose fix(Set<ConstraintViolation<DockerCompose>> violations, DockerCompose dockerCompose) {

        for (ConstraintViolation<DockerCompose> violation : violations) {
            final Path propertyPath = violation.getPropertyPath();
            if(propertyPath.toString().contains("label")) {

                ServiceSpec serviceSpec = null;
                String serviceName = "invalid";
                for (Path.Node node : propertyPath) {


                    if(node.getName().contains("service")) {
                        serviceSpec = (ServiceSpec) ((NodeImpl) node).getValue();
                        serviceName = propertyPath.toString()
                                                  .substring(propertyPath.toString().indexOf('[')+1, propertyPath.toString()
                                                                                                             .indexOf(']'));
                    }
                    if(node.getName().equals("labels")) {
                        if (serviceSpec == null) {
                            continue;
                        }
                        List<String> value = (List<String>) ((NodeImpl) node).getValue();
                        final String userLabel = DockerLabelService.LABEL_USER_KEY + "=" + dockerCompose.getHsbUsername();
                        if(null == value) {
                            value = ImmutableList.of(userLabel);
                        } else {
                            value = ImmutableList.<String>builder().addAll(value).add(userLabel).build();
                        }
                        logger.debug("Fixed: Added user label for service '{}'", serviceName);
                        serviceSpec.labels = ((ImmutableList<String>) value).asList();
                        serviceSpec = null;
                    }
                }
            }
        }
        return dockerCompose;
    }
}
