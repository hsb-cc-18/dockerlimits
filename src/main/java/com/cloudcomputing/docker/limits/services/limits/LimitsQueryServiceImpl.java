package com.cloudcomputing.docker.limits.services.limits;

import com.cloudcomputing.docker.limits.model.config.Config;
import com.cloudcomputing.docker.limits.model.stats.Stats;
import com.cloudcomputing.docker.limits.model.userrole.Role;
import com.cloudcomputing.docker.limits.services.users.UserRoleService;
import com.github.rozidan.springboot.logger.Loggable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Loggable
public class LimitsQueryServiceImpl implements LimitsQueryService {

    private final UserRoleService userRoleService;
    private final Config config;

    @Autowired
    public LimitsQueryServiceImpl(UserRoleService userRoleService, Config config) {
        this.userRoleService = userRoleService;
        this.config = config;
    }

    @Override
    public Stats getLimitsForUsername(String username) {
        final Role roleOfUser = userRoleService.getRoleForUsername(username);
        return new ResourceDescriptor(config.getMem_limit(roleOfUser.name()), config.getCpu_shares(roleOfUser.name()), config.getBlkio_weight(roleOfUser.name()));
    }
}
