package com.cloudcomputing.docker.limits.services.resource;

import com.cloudcomputing.docker.limits.model.io.DockerCompose;
import com.cloudcomputing.docker.limits.model.stats.ResourceDescriptor;
import de.xn__ho_hia.storage_unit.Megabyte;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DockerComposeResourceAnalyzerServiceImpl implements DockerComposeResourceAnalyzerService {

    public static final String COULD_NOT_SUM_MEM_OF_DOCKER_COMPOSE = "Could not sum mem of docker compose.";

    @Override
    public ResourceDescriptor sumResources(DockerCompose dockerCompose) {


        final Optional<Megabyte> mem_limit_requestOpt = dockerCompose.getServices().values()
                                                                     .stream()
                                                                     .filter(s -> s.mem_limit != null && !s.mem_limit.trim().isEmpty())
                                                                     .map(s -> s.mem_limit)
                                                                     .map(ResourceDescriptor::toBytes)
                                                                     .map(Megabyte::valueOf)
                                                                     .reduce(Megabyte::add);

        int cpushares_requested = dockerCompose.getServices()
                                     .values()
                                     .stream()
                                     .filter(s -> s.cpu_shares != null && s.cpu_shares >= 0)
                                     .map(s -> s.cpu_shares)
                                     .mapToInt(Integer::intValue)
                                     .sum();

        int blkio_weight_requested = dockerCompose.getServices()
                                     .values()
                                     .stream()
                                     .filter(s -> s.blkio_config != null && s.blkio_config.weight >= 0)
                                     .map(s -> s.blkio_config.weight)
                                     .mapToInt(Integer::intValue)
                                     .sum();

        if(!mem_limit_requestOpt.isPresent()) {
            throw new IllegalStateException(COULD_NOT_SUM_MEM_OF_DOCKER_COMPOSE);
        }

        return new ResourceDescriptor(mem_limit_requestOpt.get().toString(), cpushares_requested, blkio_weight_requested);
    }
}
