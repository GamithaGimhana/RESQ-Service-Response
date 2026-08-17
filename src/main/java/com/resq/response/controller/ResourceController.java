package com.resq.response.controller;

import com.resq.response.document.Resource;
import com.resq.response.dto.ResourceCreateRequest;
import com.resq.response.dto.ResourceUpdateRequest;
import com.resq.response.model.ResourceCategory;
import com.resq.response.model.ResourceStatus;
import com.resq.response.service.ResourceService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/response/resources")
public class ResourceController {

    private final ResourceService resourceService;

    @Autowired
    public ResourceController(ResourceService resourceService) {
        this.resourceService = resourceService;
    }

    @PostMapping
    public ResponseEntity<Resource> createResource(@Valid @RequestBody ResourceCreateRequest request) {
        Resource created = resourceService.createResource(request);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Resource>> getResources(
            @RequestParam(required = false) ResourceCategory category,
            @RequestParam(required = false) ResourceStatus status) {
        List<Resource> resources = resourceService.getResources(category, status);
        return ResponseEntity.ok(resources);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Resource> getResourceById(@PathVariable String id) {
        Resource resource = resourceService.getResourceByIdOrCode(id);
        return ResponseEntity.ok(resource);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Resource> updateResource(
            @PathVariable String id,
            @Valid @RequestBody ResourceUpdateRequest request) {
        Resource updated = resourceService.updateResource(id, request);
        return ResponseEntity.ok(updated);
    }
}
