package com.cloudcomputing.docker.limits.services.resource;

import com.cloudcomputing.docker.limits.model.io.DockerCompose;
import com.cloudcomputing.docker.limits.model.stats.Stats;
import com.github.rozidan.springboot.logger.Loggable;
import de.xn__ho_hia.storage_unit.Megabyte;
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
    private final static Megabyte mem_limit_role = StorageUnits.megabyte(2048);
    private final static int cpu_shares_role = 1024;

    @Autowired
    public ResourceAuthorizeServiceImpl(ResourceUsageService resourceUsageService, DockerComposeResourceAnalyzerService dockerComposeResourceAnalyzerService) {
        this.resourceUsageService = resourceUsageService;
        this.dockerComposeResourceAnalyzerService = dockerComposeResourceAnalyzerService;
    }

    @Override
    public boolean isAuthorized(DockerCompose dockerCompose) {
        boolean isAuthorized = false;

        final Stats usedResources = resourceUsageService.sumResourceUsage(dockerCompose.getHsbUsername());
        final Stats requestedResources = dockerComposeResourceAnalyzerService.sumResources(dockerCompose);
        final Stats wouldAllocResources = usedResources.add(requestedResources);

        if(mem_limitFits(wouldAllocResources) && cpu_sharesFits(wouldAllocResources)) {
            isAuthorized = true;
        } else {
            isAuthorized = false;
            logger.debug("Request exceeds limit: requested resources {} does not fit.", requestedResources);
        }

        return isAuthorized;
    }

    private boolean cpu_sharesFits(Stats wouldAllocResources) {
        return cpu_shares_role - wouldAllocResources.getCpu_shares() > 0;
    }

    private boolean mem_limitFits(Stats wouldAllocResources) {
        return mem_limit_role.subtract(wouldAllocResources.getMem_limit()).longValue() > 0;
    }

}
