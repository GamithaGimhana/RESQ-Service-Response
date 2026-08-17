package com.resq.response.controller;

import com.resq.response.document.ResourceAllocation;
import com.resq.response.dto.AllocationCreateRequest;
import com.resq.response.service.ResourceService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/response/allocations")
public class AllocationController {

    private final ResourceService resourceService;

    @Autowired
    public AllocationController(ResourceService resourceService) {
        this.resourceService = resourceService;
    }

    @PostMapping
    public ResponseEntity<ResourceAllocation> allocateResource(
            @Valid @RequestBody AllocationCreateRequest request,
            @RequestHeader(value = "X-User-Id", required = false) String userId) {
        if (request.getAllocatedBy() == null && userId != null) {
            request.setAllocatedBy(userId);
        }
        ResourceAllocation allocation = resourceService.allocateResource(request);
        return new ResponseEntity<>(allocation, HttpStatus.CREATED);
    }

    @PostMapping("/{id}/release")
    public ResponseEntity<ResourceAllocation> releaseAllocation(@PathVariable String id) {
        ResourceAllocation released = resourceService.releaseAllocation(id);
        return ResponseEntity.ok(released);
    }

    @GetMapping("/incident/{incidentId}")
    public ResponseEntity<List<ResourceAllocation>> getAllocationsByIncident(@PathVariable String incidentId) {
        List<ResourceAllocation> list = resourceService.getAllocationsByIncident(incidentId);
        return ResponseEntity.ok(list);
    }
}
