package com.cloudcomputing.docker.limits.model.validator;

import com.cloudcomputing.docker.limits.model.io.DockerCompose;
import com.cloudcomputing.docker.limits.model.io.ServiceSpec;
import com.cloudcomputing.docker.limits.services.label.DockerLabelService;
import com.github.rozidan.springboot.logger.Loggable;
import org.hibernate.validator.HibernateValidator;
import org.hibernate.validator.HibernateValidatorConfiguration;
import org.hibernate.validator.cfg.ConstraintMapping;
import org.hibernate.validator.cfg.GenericConstraintDef;
import org.hibernate.validator.cfg.defs.NotEmptyDef;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;

import static java.lang.annotation.ElementType.FIELD;

@Component
@Loggable
public class DockerComposeValidatorImpl implements DockerComposeValidator {

    private Validator validator;

    public DockerComposeValidatorImpl() {
        init();
    }

    private void init() {
        HibernateValidatorConfiguration configuration = Validation
                .byProvider( HibernateValidator.class )
                .configure();

        ConstraintMapping constraintMapping = configuration.createConstraintMapping();

        constraintMapping
                .type( DockerCompose.class )
                    .ignoreAllAnnotations()
                    .method( "getVersion" )
                        .returnValue()
                        .constraint( new NotEmptyDef() )
                    .method( "getHsbUsername" )
                        .returnValue()
                        .constraint( new NotEmptyDef() )
                .method("getServices")
                    .returnValue()
                    .valid()
                    .type( ServiceSpec.class )
                        .ignoreAllAnnotations()
                        .property( "labels", FIELD )
                        .valid()
                            .constraint( new GenericConstraintDef<>(ContainsLabelKeys.class)
                                    .param( "value", new String[]{ DockerLabelService.LABEL_USER_KEY } ) )
        ;

        validator = configuration.addMapping( constraintMapping )
                                           .buildValidatorFactory()
                                           .getValidator();

    }

    @Override
    public Set<ConstraintViolation<DockerCompose>> validate(DockerCompose dockerCompose) {
        Set<ConstraintViolation<DockerCompose>> violations = validator.validate(dockerCompose);
        return violations;
    }

}
