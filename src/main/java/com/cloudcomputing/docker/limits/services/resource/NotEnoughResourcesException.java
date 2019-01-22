package com.cloudcomputing.docker.limits.services.resource;

import com.cloudcomputing.docker.limits.model.stats.ResourceDescriptor;
import de.xn__ho_hia.storage_unit.Megabyte;
import io.netty.util.internal.StringUtil;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class NotEnoughResourcesException extends RuntimeException {
    ResourceDescriptor wouldAllocResources;
    ResourceDescriptor limits;

    public NotEnoughResourcesException(ResourceDescriptor wouldAllocResources, ResourceDescriptor limits) {
        this.wouldAllocResources = wouldAllocResources;
        this.limits = limits;
    }

    @Override
    public String getMessage() {
        int needetCPUShares = wouldAllocResources.getCpu_shares() - limits.getCpu_shares();
        long needetRAM = wouldAllocResources.getMem_limit().subtract(limits.getMem_limit()).longValue();
        Megabyte needetMegabyte= Megabyte.valueOf(needetRAM);
        int needetBlkIO = wouldAllocResources.getBlkio_weight() - limits.getBlkio_weight();
        String returnString =  "Please reduce your requested resources by ";
        List<String> errors = new ArrayList<>();

        if (needetCPUShares>0){
            errors.add(needetCPUShares + " of CPU shares");
        }
        if(needetMegabyte.longValue()>0){
            errors.add(needetMegabyte + " RAM");
        }
        if(needetBlkIO>0){
            errors.add(needetBlkIO+" of Block IO");
        }
        returnString+= String.join(", ",errors) + ".";
        return  returnString;
    }
}
