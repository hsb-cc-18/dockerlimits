package com.cloudcomputing.docker.limits.services.resource;

import com.cloudcomputing.docker.limits.model.io.DockerCompose;
import com.cloudcomputing.docker.limits.services.stats.Stats;
import de.xn__ho_hia.storage_unit.Megabyte;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DockerComposeResourceAnalyzerServiceImpl implements DockerComposeResourceAnalyzerService {
    @Override
    public Stats sumResources(DockerCompose dockerCompose) {


        final Optional<Megabyte> mem_limit_requestOpt = dockerCompose.getServices().values()
                                                                     .stream()
                                                                     .filter(s -> s.mem_limit != null && !s.mem_limit.trim().isEmpty())
                                                                     .map(s -> s.mem_limit)
                                                                     .map(Stats::toBytes)
                                                                     .map(Megabyte::valueOf)
                                                                     .reduce(Megabyte::add);

        int cpu_requested = dockerCompose.getServices()
                                     .values()
                                     .stream()
                                     .filter(s -> s.cpu_percent != null && s.cpu_percent >= 0)
                                     .map(s -> s.cpu_percent)
                                     .mapToInt(Integer::intValue)
                                     .sum();

        if(!mem_limit_requestOpt.isPresent()) {
            throw new IllegalStateException("Could not sum mem of docker compose.");
        }

        return new Stats(mem_limit_requestOpt.get().toString(), cpu_requested);
    }
}
