package com.resq.response.service;

import com.resq.response.document.Resource;
import com.resq.response.document.ResourceAllocation;
import com.resq.response.dto.AllocationCreateRequest;
import com.resq.response.dto.ResourceCreateRequest;
import com.resq.response.dto.ResourceUpdateRequest;
import com.resq.response.dto.ResponseStatistics;
import com.resq.response.model.ResourceCategory;
import com.resq.response.model.ResourceStatus;

import java.util.List;

public interface ResourceService {
    Resource createResource(ResourceCreateRequest request);
    List<Resource> getResources(ResourceCategory category, ResourceStatus status);
    Resource getResourceByIdOrCode(String idOrCode);
    Resource updateResource(String id, ResourceUpdateRequest request);
    
    ResourceAllocation allocateResource(AllocationCreateRequest request);
    ResourceAllocation releaseAllocation(String allocationId);
    List<ResourceAllocation> getAllocationsByIncident(String incidentId);
    
    ResponseStatistics getStatistics();
}
