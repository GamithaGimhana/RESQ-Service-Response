package com.resq.response.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public class AllocationCreateRequest {

    @NotBlank(message = "Incident ID is required")
    private String incidentId;

    @NotBlank(message = "Resource ID is required")
    private String resourceId;

    @Min(value = 1, message = "Allocated quantity must be at least 1")
    private int quantity;

    private String allocatedBy;

    public AllocationCreateRequest() {
    }

    public AllocationCreateRequest(String incidentId, String resourceId, int quantity, String allocatedBy) {
        this.incidentId = incidentId;
        this.resourceId = resourceId;
        this.quantity = quantity;
        this.allocatedBy = allocatedBy;
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

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getAllocatedBy() {
        return allocatedBy;
    }

    public void setAllocatedBy(String allocatedBy) {
        this.allocatedBy = allocatedBy;
    }
}
