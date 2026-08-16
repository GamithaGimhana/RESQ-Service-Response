package com.resq.response.repository;

import com.resq.response.document.ResourceAllocation;
import com.resq.response.model.AllocationStatus;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ResourceAllocationRepository extends MongoRepository<ResourceAllocation, String> {
    List<ResourceAllocation> findByIncidentId(String incidentId);
    List<ResourceAllocation> findByResourceIdAndStatus(String resourceId, AllocationStatus status);
    List<ResourceAllocation> findByStatus(AllocationStatus status);
    long countByStatus(AllocationStatus status);
}
