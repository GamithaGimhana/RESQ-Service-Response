package com.resq.response.dto;

import com.resq.response.model.ResourceCategory;
import com.resq.response.model.ResourceStatus;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class ResourceCreateRequest {

    private String resourceCode;

    @NotBlank(message = "Resource name is required")
    private String name;

    @NotNull(message = "Category is required")
    private ResourceCategory category;

    @Min(value = 1, message = "Quantity must be at least 1")
    private int quantity;

    private Integer availableQuantity;

    private ResourceStatus status = ResourceStatus.AVAILABLE;

    private String location;

    public ResourceCreateRequest() {
    }

    public String getResourceCode() {
        return resourceCode;
    }

    public void setResourceCode(String resourceCode) {
        this.resourceCode = resourceCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ResourceCategory getCategory() {
        return category;
    }

    public void setCategory(ResourceCategory category) {
        this.category = category;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Integer getAvailableQuantity() {
        return availableQuantity;
    }

    public void setAvailableQuantity(Integer availableQuantity) {
        this.availableQuantity = availableQuantity;
    }

    public ResourceStatus getStatus() {
        return status;
    }

    public void setStatus(ResourceStatus status) {
        this.status = status;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
