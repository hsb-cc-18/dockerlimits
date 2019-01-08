package com.cloudcomputing.docker.limits.model.io;

import org.hibernate.validator.HibernateValidator;
import org.hibernate.validator.HibernateValidatorConfiguration;
import org.hibernate.validator.cfg.ConstraintMapping;
import org.hibernate.validator.cfg.defs.NotEmptyDef;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.lang.annotation.ElementType;
import java.util.Set;

@Component
public class DockerComposeValidatorImpl implements DockerComposeValidator {

    @Override
    public Set<ConstraintViolation<DockerCompose>> validate(DockerCompose dockerCompose) {

        HibernateValidatorConfiguration configuration = Validation
                .byProvider( HibernateValidator.class )
                .configure();

        ConstraintMapping constraintMapping = configuration.createConstraintMapping();

        constraintMapping
                .type( DockerCompose.class )
                    .ignoreAllAnnotations()
                    .property("services.labels", ElementType.METHOD)
                        .constraint( new NotEmptyDef() )
                    .method( "getVersion" )
                        .returnValue()
                        .constraint( new NotEmptyDef() )
                    .method( "getHsbUsername" )
                        .returnValue()
                        .constraint( new NotEmptyDef() )

        ;

        Validator validator = configuration.addMapping( constraintMapping )
                                           .buildValidatorFactory()
                                           .getValidator();


        Set<ConstraintViolation<DockerCompose>> errors = validator.validate(dockerCompose);

        return errors;
    }
}
