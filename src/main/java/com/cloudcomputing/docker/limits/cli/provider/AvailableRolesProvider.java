package com.cloudcomputing.docker.limits.cli.provider;

import com.cloudcomputing.docker.limits.model.userrole.Role;
import org.apache.commons.lang3.EnumUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.MethodParameter;
import org.springframework.shell.CompletionContext;
import org.springframework.shell.CompletionProposal;
import org.springframework.shell.standard.ValueProviderSupport;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class AvailableRolesProvider extends ValueProviderSupport {

    /**
     * {@inheritDoc}
     *
     * @see org.springframework.shell.standard.ValueProvider#complete(org.springframework.core.MethodParameter,
     *      org.springframework.shell.CompletionContext, java.lang.String[])
     */
    @Override
    public List<CompletionProposal> complete(MethodParameter parameter, CompletionContext completionContext, String[] hints) {
        String prefix = completionContext.currentWordUpToCursor();
        return getRoles().stream()
                         .filter(s -> StringUtils.startsWithIgnoreCase(s, prefix))
                         .map(CompletionProposal::new).collect(Collectors.toList());
    }

    /**
     * Gets the files.
     *
     * @return the roles
     */
    private List<String> getRoles() {
        return EnumUtils.getEnumList(Role.class)
                        .stream()
                        .map(Enum::name)
                        .collect(Collectors.toList());
    }
}
