package com.resq.response.document;

import com.resq.response.model.AllocationStatus;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "resource_allocations")
public class ResourceAllocation {

    @Id
    private String id;

    @Indexed
    private String incidentId;

    @Indexed
    private String resourceId;

    private String resourceCode;

    private String resourceName;

    private int allocatedQuantity;

    @Indexed
    private AllocationStatus status = AllocationStatus.ALLOCATED;

    private String allocatedBy;

    private LocalDateTime allocatedAt = LocalDateTime.now();

    private LocalDateTime releasedAt;

    public ResourceAllocation() {
    }

    public ResourceAllocation(String incidentId, String resourceId, String resourceCode, String resourceName, int allocatedQuantity, String allocatedBy) {
        this.incidentId = incidentId;
        this.resourceId = resourceId;
        this.resourceCode = resourceCode;
        this.resourceName = resourceName;
        this.allocatedQuantity = allocatedQuantity;
        this.allocatedBy = allocatedBy;
        this.status = AllocationStatus.ALLOCATED;
        this.allocatedAt = LocalDateTime.now();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIncidentId() {
        return incidentId;
    }

    public void setIncidentId(String incidentId) {
        this.incidentId = incidentId;
    }

    public String getResourceId() {
        return resourceId;
    }

    public void setResourceId(String resourceId) {
        this.resourceId = resourceId;
    }

    public String getResourceCode() {
        return resourceCode;
    }

    public void setResourceCode(String resourceCode) {
        this.resourceCode = resourceCode;
    }

    public String getResourceName() {
        return resourceName;
    }

    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }

    public int getAllocatedQuantity() {
        return allocatedQuantity;
    }

    public void setAllocatedQuantity(int allocatedQuantity) {
        this.allocatedQuantity = allocatedQuantity;
    }

    public AllocationStatus getStatus() {
        return status;
    }

    public void setStatus(AllocationStatus status) {
        this.status = status;
    }

    public String getAllocatedBy() {
        return allocatedBy;
    }

    public void setAllocatedBy(String allocatedBy) {
        this.allocatedBy = allocatedBy;
    }

    public LocalDateTime getAllocatedAt() {
        return allocatedAt;
    }

    public void setAllocatedAt(LocalDateTime allocatedAt) {
        this.allocatedAt = allocatedAt;
    }

    public LocalDateTime getReleasedAt() {
        return releasedAt;
    }

    public void setReleasedAt(LocalDateTime releasedAt) {
        this.releasedAt = releasedAt;
    }
}
