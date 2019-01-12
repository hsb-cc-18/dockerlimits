package com.cloudcomputing.docker.limits.services.resource;

import com.cloudcomputing.docker.limits.model.io.DockerCompose;
import com.cloudcomputing.docker.limits.services.stats.Stats;
import com.github.rozidan.springboot.logger.Loggable;
import de.xn__ho_hia.storage_unit.Mebibyte;
import de.xn__ho_hia.storage_unit.StorageUnits;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Loggable
public class ResourceCheckerServiceImpl implements ResourceCheckerService {

    private Logger logger = LoggerFactory.getLogger(getClass());

    private final ResourceUsageService resourceUsageService;
    private final DockerComposeRequestedResourcesService dockerComposeRequestedResourcesService;
    //TODO: dynamic depending on user role
    private final Mebibyte mem_limit_role = StorageUnits.mebibyte(2048);

    @Autowired
    public ResourceCheckerServiceImpl(ResourceUsageService resourceUsageService, DockerComposeRequestedResourcesService dockerComposeRequestedResourcesService) {
        this.resourceUsageService = resourceUsageService;
        this.dockerComposeRequestedResourcesService = dockerComposeRequestedResourcesService;
    }

    @Override
    public boolean check(DockerCompose dockerCompose) {
        boolean fits = false;

        final Stats usedResources = resourceUsageService.summarizeResourceUsage(dockerCompose.getHsbUsername());
        final Stats requestedResources = dockerComposeRequestedResourcesService.getRequestedResources(dockerCompose);

        if(mem_limitFits(usedResources, requestedResources) && cpu_percentFits()) {
            fits = true;
        } else {
            fits = false;
            logger.debug("Request exceeds limit");
        }

        return fits;
    }

    private boolean cpu_percentFits() {
        //TODO: implement
        return true;
    }

    private boolean mem_limitFits(Stats usedResources, Stats requestedResources) {
        return mem_limit_role.subtract(requestedResources.mem_limit.add(usedResources.mem_limit)).longValue() > 0;
    }

}
