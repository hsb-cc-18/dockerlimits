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
public class ResourceAuthorizeServiceImpl implements ResourceAuthorizeService {

    private Logger logger = LoggerFactory.getLogger(getClass());

    private final ResourceUsageService resourceUsageService;
    private final DockerComposeResourceAnalyzerService dockerComposeResourceAnalyzerService;
    //TODO: dynamic depending on user role
    private final Mebibyte mem_limit_role = StorageUnits.mebibyte(2048);

    @Autowired
    public ResourceAuthorizeServiceImpl(ResourceUsageService resourceUsageService, DockerComposeResourceAnalyzerService dockerComposeResourceAnalyzerService) {
        this.resourceUsageService = resourceUsageService;
        this.dockerComposeResourceAnalyzerService = dockerComposeResourceAnalyzerService;
    }

    @Override
    public boolean isAuthorized(DockerCompose dockerCompose) {
        boolean authorized = false;

        final Stats usedResources = resourceUsageService.sumResourceUsage(dockerCompose.getHsbUsername());
        final Stats requestedResources = dockerComposeResourceAnalyzerService.sumResources(dockerCompose);

        if(mem_limitFits(usedResources, requestedResources) && cpu_percentFits()) {
            authorized = true;
        } else {
            authorized = false;
            logger.debug("Request exceeds limit");
        }

        return authorized;
    }

    private boolean cpu_percentFits() {
        //TODO: implement
        return true;
    }

    private boolean mem_limitFits(Stats usedResources, Stats requestedResources) {
        return mem_limit_role.subtract(requestedResources.mem_limit.add(usedResources.mem_limit)).longValue() > 0;
    }

}
