package com.cloudcomputing.docker.limits.services.resource;

import com.cloudcomputing.docker.limits.model.io.DockerCompose;
import com.cloudcomputing.docker.limits.services.stats.Stats;
import com.github.rozidan.springboot.logger.Loggable;
import de.xn__ho_hia.storage_unit.Megabyte;
import de.xn__ho_hia.storage_unit.StorageUnits;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Service
@Loggable
public class ResourceAuthorizeServiceImpl implements ResourceAuthorizeService {

    private Logger logger = LoggerFactory.getLogger(getClass());

    private final ResourceUsageService resourceUsageService;
    private final DockerComposeResourceAnalyzerService dockerComposeResourceAnalyzerService;
    //TODO: dynamic depending on user role
    private final static Megabyte mem_limit_role = StorageUnits.megabyte(2048);
    private final static int cpu_percent_role = 100;

    @Autowired
    public ResourceAuthorizeServiceImpl(ResourceUsageService resourceUsageService, DockerComposeResourceAnalyzerService dockerComposeResourceAnalyzerService) {
        this.resourceUsageService = resourceUsageService;
        this.dockerComposeResourceAnalyzerService = dockerComposeResourceAnalyzerService;
    }

    @Override
    public boolean isAuthorized(DockerCompose dockerCompose) {
        boolean isAuthorized = false;

        final CompletableFuture<Stats> usedResources = resourceUsageService.sumResourceUsage(dockerCompose.getHsbUsername());
        final CompletableFuture<Stats> requestedResources = dockerComposeResourceAnalyzerService.sumResources(dockerCompose);

        try {
            CompletableFuture.allOf(usedResources, requestedResources).join();

            final Stats wouldAllocResources = usedResources.get().add(requestedResources.get());

            if(mem_limitFits(wouldAllocResources) && cpu_percentFits(wouldAllocResources)) {
                isAuthorized = true;
            } else {
                isAuthorized = false;
                logger.debug("Request exceeds limit");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return isAuthorized;
    }

    private boolean cpu_percentFits(Stats wouldAllocReources) {
        return cpu_percent_role - wouldAllocReources.cpu_percent > 0;
    }

    private boolean mem_limitFits(Stats wouldAllocReources) {
        return mem_limit_role.subtract(wouldAllocReources.mem_limit).longValue() > 0;
    }

}
