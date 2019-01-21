package com.cloudcomputing.docker.limits.services.resource;

import com.cloudcomputing.docker.limits.model.io.DockerCompose;
import com.cloudcomputing.docker.limits.model.stats.ResourceDescriptor;
import com.cloudcomputing.docker.limits.services.limits.LimitsQueryService;
import com.github.rozidan.springboot.logger.Loggable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Loggable
public class ResourceAuthorizeServiceImpl implements ResourceAuthorizeService {

    private static final Logger logger = LoggerFactory.getLogger(ResourceAuthorizeServiceImpl.class);
    private final LimitsQueryService limitsQueryService;
    private final ResourceUsageService resourceUsageService;
    private final DockerComposeResourceAnalyzerService dockerComposeResourceAnalyzerService;

    @Autowired
    public ResourceAuthorizeServiceImpl(LimitsQueryService limitsQueryService, ResourceUsageService resourceUsageService, DockerComposeResourceAnalyzerService dockerComposeResourceAnalyzerService) {
        this.limitsQueryService = limitsQueryService;
        this.resourceUsageService = resourceUsageService;
        this.dockerComposeResourceAnalyzerService = dockerComposeResourceAnalyzerService;
    }

    @Override
    public boolean isAuthorized(DockerCompose dockerCompose) {
        boolean isAuthorized = false;

        final ResourceDescriptor usedResources = resourceUsageService.sumResourceUsage(dockerCompose.getHsbUsername());
        final ResourceDescriptor requestedResources = dockerComposeResourceAnalyzerService.sumResources(dockerCompose);
        final ResourceDescriptor wouldAllocResources = usedResources.add(requestedResources);
        final ResourceDescriptor limits = limitsQueryService.getLimitsForUsername(dockerCompose.getHsbUsername());

        if (
                mem_limitFits(wouldAllocResources, limits)
                && cpu_sharesFits(wouldAllocResources, limits)
                && blkio_weightFits(wouldAllocResources, limits)
        ) {
            isAuthorized = true;
        } else {
            isAuthorized = false;
            logger.debug("Request exceeds limit: requested resources {} does not fit.", requestedResources);
        }

        return isAuthorized;
    }

    private boolean blkio_weightFits(ResourceDescriptor wouldAllocResources, ResourceDescriptor limits) {
        return limits.getBlkio_weight() - wouldAllocResources.getBlkio_weight() > 0;
    }

    private boolean cpu_sharesFits(ResourceDescriptor wouldAllocResources, ResourceDescriptor limits) {
        return limits.getCpu_shares() - wouldAllocResources.getCpu_shares() > 0;
    }

    private boolean mem_limitFits(ResourceDescriptor wouldAllocResources, ResourceDescriptor limits) {
        return limits.getMem_limit().subtract(wouldAllocResources.getMem_limit()).longValue() > 0;
    }

}
