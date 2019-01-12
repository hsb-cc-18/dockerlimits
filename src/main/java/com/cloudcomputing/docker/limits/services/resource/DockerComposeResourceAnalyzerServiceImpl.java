package com.cloudcomputing.docker.limits.services.resource;

import com.cloudcomputing.docker.limits.model.io.DockerCompose;
import com.cloudcomputing.docker.limits.services.stats.Stats;
import de.xn__ho_hia.storage_unit.Mebibyte;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DockerComposeResourceAnalyzerServiceImpl implements DockerComposeResourceAnalyzerService {
    @Override
    public Stats sumResources(DockerCompose dockerCompose) {


        final Optional<Mebibyte> mem_limit_requestOpt = dockerCompose.getServices().entrySet()
                                                                     .stream()
                                                                     .map(es -> es.getValue())
                                                                     .filter(s -> s.mem_limit != null && !s.mem_limit.trim().isEmpty())
                                                                     .map(s -> s.mem_limit)
                                                                     .map(Stats::toBytes)
                                                                     .map(Mebibyte::valueOf)
                                                                     .reduce(Mebibyte::add);
        if(!mem_limit_requestOpt.isPresent()) {
            throw new IllegalStateException("Could not sum mem of docker compose.");
        }

        //TODO:
        Integer cpu_requested = 0;

        return new Stats(mem_limit_requestOpt.get().toString(), cpu_requested);
    }
}
