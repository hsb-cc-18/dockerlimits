package com.cloudcomputing.docker.limits.cli;

import com.cloudcomputing.docker.limits.model.stats.ResourceDescriptor;
import com.cloudcomputing.docker.limits.model.userrole.Role;
import com.cloudcomputing.docker.limits.services.limits.LimitsQueryService;
import com.cloudcomputing.docker.limits.services.resource.ResourceUsageService;
import com.cloudcomputing.docker.limits.services.users.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

@ShellComponent
public class UsageSummary {

    private final ResourceUsageService resourceUsageService;
    private final LimitsQueryService limitsQueryService;
    private final UserRoleService userRoleService;

    @Autowired
    UsageSummary(ResourceUsageService resourceUsageService, LimitsQueryService limitsQueryService, UserRoleService userRoleService) {
        this.resourceUsageService = resourceUsageService;
        this.limitsQueryService = limitsQueryService;
        this.userRoleService = userRoleService;
    }

    @ShellMethod(key = "usage-summary", value = "Summarize resource usage of a user")
    public void usage( @ShellOption(help = "the username") final String username) {
        final Role role = userRoleService.getRoleForUsername(username);
        System.out.printf("User '%s' has role '%s'%n", username, role.name());
        final ResourceDescriptor resourceLimits = limitsQueryService.getLimitsForUsername(username);
        System.out.printf("The resource limits are: %s%n", resourceLimits);
        final ResourceDescriptor resourceUsage = resourceUsageService.sumResourceUsage(username);
        System.out.printf("Resource usage for user '%s': %s%n", username, resourceUsage);

    }
}
