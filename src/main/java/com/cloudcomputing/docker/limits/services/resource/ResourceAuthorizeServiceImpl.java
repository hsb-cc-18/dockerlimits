package com.cloudcomputing.docker.limits.services.resource;

import com.cloudcomputing.docker.limits.model.io.DockerCompose;
import com.cloudcomputing.docker.limits.model.stats.ResourceDescriptor;
import com.cloudcomputing.docker.limits.services.limits.LimitsQueryService;
import com.github.rozidan.springboot.logger.Loggable;
import de.xn__ho_hia.storage_unit.Megabyte;
import de.xn__ho_hia.storage_unit.StorageUnits;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Service
@Loggable(ignore = NotEnoughResourcesException.class)
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
        final ResourceDescriptor usedResources = resourceUsageService.sumResourceUsage(dockerCompose.getHsbUsername());
        final ResourceDescriptor requestedResources = dockerComposeResourceAnalyzerService.sumResources(dockerCompose);
        final ResourceDescriptor wouldAllocResources = usedResources.add(requestedResources);
        final ResourceDescriptor limits = limitsQueryService.getLimitsForUsername(dockerCompose.getHsbUsername());

        final List<String> errors = resourceErrors(wouldAllocResources.subtract(limits));
        if (errors.isEmpty()) {
            logger.debug("Request {} fits user limits. Authorized.", requestedResources);
            return true;
        } else {
            logger.debug("Request exceeds limit: requested resources {} does not fit.", requestedResources);
            throw new NotEnoughResourcesException(errors);
        }

    }

    private List<String> resourceErrors(@Nonnull final ResourceDescriptor potentialExceedingResources) {

        final int neededCPUShares = potentialExceedingResources.getCpu_shares();
        final Megabyte neededRAM = potentialExceedingResources.getMem_limit();
        final int neededBlkio = potentialExceedingResources.getBlkio_weight();

        List<String> errors = new ArrayList<>();

        if (neededCPUShares > 0) {
            errors.add(neededCPUShares + " of CPU shares");
        }
        if (neededRAM.longValue() > 0) {
            errors.add(StorageUnits.formatAsMegabyte(neededRAM.longValue(), "0.00", Locale.ENGLISH) + " RAM");
        }
        if (neededBlkio > 0) {
            errors.add(neededBlkio + " of Block IO");
        }

        return errors;
    }

}
