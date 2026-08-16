package com.resq.response.service.impl;

import com.resq.response.document.Resource;
import com.resq.response.document.ResourceAllocation;
import com.resq.response.dto.AllocationCreateRequest;
import com.resq.response.dto.ResourceCreateRequest;
import com.resq.response.dto.ResourceUpdateRequest;
import com.resq.response.dto.ResponseStatistics;
import com.resq.response.model.AllocationStatus;
import com.resq.response.model.ResourceCategory;
import com.resq.response.model.ResourceStatus;
import com.resq.response.model.TeamStatus;
import com.resq.response.model.TeamType;
import com.resq.response.repository.ResourceAllocationRepository;
import com.resq.response.repository.ResourceRepository;
import com.resq.response.repository.ResponseTeamRepository;
import com.resq.response.service.ResourceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
public class ResourceServiceImpl implements ResourceService {

    private static final Logger log = LoggerFactory.getLogger(ResourceServiceImpl.class);

    private final ResourceRepository resourceRepository;
    private final ResourceAllocationRepository allocationRepository;
    private final ResponseTeamRepository teamRepository;

    @Autowired
    public ResourceServiceImpl(
            ResourceRepository resourceRepository,
            ResourceAllocationRepository allocationRepository,
            ResponseTeamRepository teamRepository) {
        this.resourceRepository = resourceRepository;
        this.allocationRepository = allocationRepository;
        this.teamRepository = teamRepository;
    }

    private String generateResourceCode(ResourceCategory category) {
        String prefix = category != null ? category.name().substring(0, Math.min(3, category.name().length())) : "RES";
        return prefix + "-" + UUID.randomUUID().toString().substring(0, 4).toUpperCase();
    }

    @Override
    public Resource createResource(ResourceCreateRequest request) {
        String code = request.getResourceCode() != null && !request.getResourceCode().trim().isEmpty()
                ? request.getResourceCode().trim()
                : generateResourceCode(request.getCategory());

        int available = request.getAvailableQuantity() != null ? request.getAvailableQuantity() : request.getQuantity();

        Resource resource = new Resource(
                code,
                request.getName(),
                request.getCategory(),
                request.getQuantity(),
                available,
                request.getStatus() != null ? request.getStatus() : ResourceStatus.AVAILABLE,
                request.getLocation()
        );

        Resource saved = resourceRepository.save(resource);
        log.info("Created resource [code={}, name={}, qty={}]", saved.getResourceCode(), saved.getName(), saved.getQuantity());
        return saved;
    }

    @Override
    public List<Resource> getResources(ResourceCategory category, ResourceStatus status) {
        if (category != null && status != null) {
            return resourceRepository.findByCategoryAndStatus(category, status);
        } else if (category != null) {
            return resourceRepository.findByCategory(category);
        } else if (status != null) {
            return resourceRepository.findByStatus(status);
        }
        return resourceRepository.findAll();
    }

    @Override
    public Resource getResourceByIdOrCode(String idOrCode) {
        return resourceRepository.findById(idOrCode)
                .or(() -> resourceRepository.findByResourceCode(idOrCode))
                .orElseThrow(() -> new NoSuchElementException("Resource not found: " + idOrCode));
    }

    @Override
    public Resource updateResource(String id, ResourceUpdateRequest request) {
        Resource resource = getResourceByIdOrCode(id);

        if (request.getName() != null) {
            resource.setName(request.getName());
        }
        if (request.getCategory() != null) {
            resource.setCategory(request.getCategory());
        }
        if (request.getQuantity() != null) {
            resource.setQuantity(request.getQuantity());
        }
        if (request.getAvailableQuantity() != null) {
            resource.setAvailableQuantity(request.getAvailableQuantity());
        }
        if (request.getStatus() != null) {
            resource.setStatus(request.getStatus());
        }
        if (request.getLocation() != null) {
            resource.setLocation(request.getLocation());
        }

        resource.setUpdatedAt(LocalDateTime.now());
        Resource updated = resourceRepository.save(resource);
        log.info("Updated resource [code={}]", updated.getResourceCode());
        return updated;
    }

    @Override
    public ResourceAllocation allocateResource(AllocationCreateRequest request) {
        Resource resource = getResourceByIdOrCode(request.getResourceId());

        if (resource.getAvailableQuantity() < request.getQuantity()) {
            throw new IllegalArgumentException("Insufficient resource inventory. Available: " + resource.getAvailableQuantity() + ", Requested: " + request.getQuantity());
        }

        resource.setAvailableQuantity(resource.getAvailableQuantity() - request.getQuantity());
        if (resource.getAvailableQuantity() == 0) {
            resource.setStatus(ResourceStatus.DEPLETED);
        }
        resourceRepository.save(resource);

        ResourceAllocation allocation = new ResourceAllocation(
                request.getIncidentId(),
                resource.getId(),
                resource.getResourceCode(),
                resource.getName(),
                request.getQuantity(),
                request.getAllocatedBy()
        );

        ResourceAllocation saved = allocationRepository.save(allocation);
        log.info("Allocated resource [resourceCode={}, incidentId={}, qty={}]", resource.getResourceCode(), request.getIncidentId(), request.getQuantity());
        return saved;
    }

    @Override
    public ResourceAllocation releaseAllocation(String allocationId) {
        ResourceAllocation allocation = allocationRepository.findById(allocationId)
                .orElseThrow(() -> new NoSuchElementException("Allocation not found with ID: " + allocationId));

        if (allocation.getStatus() == AllocationStatus.RELEASED) {
            return allocation;
        }

        allocation.setStatus(AllocationStatus.RELEASED);
        allocation.setReleasedAt(LocalDateTime.now());
        ResourceAllocation updated = allocationRepository.save(allocation);

        // Restore quantity to resource
        try {
            Resource resource = getResourceByIdOrCode(allocation.getResourceId());
            resource.setAvailableQuantity(resource.getAvailableQuantity() + allocation.getAllocatedQuantity());
            if (resource.getStatus() == ResourceStatus.DEPLETED && resource.getAvailableQuantity() > 0) {
                resource.setStatus(ResourceStatus.AVAILABLE);
            }
            resourceRepository.save(resource);
            log.info("Released resource allocation [id={}, resourceCode={}, restoredQty={}]", allocationId, resource.getResourceCode(), allocation.getAllocatedQuantity());
        } catch (Exception e) {
            log.warn("Could not find resource {} to restore inventory: {}", allocation.getResourceId(), e.getMessage());
        }

        return updated;
    }

    @Override
    public List<ResourceAllocation> getAllocationsByIncident(String incidentId) {
        return allocationRepository.findByIncidentId(incidentId);
    }

    @Override
    public ResponseStatistics getStatistics() {
        long totalTeams = teamRepository.count();
        long availableTeams = teamRepository.countByStatus(TeamStatus.AVAILABLE);
        long deployedTeams = teamRepository.countByStatus(TeamStatus.DEPLOYED);

        List<Resource> resources = resourceRepository.findAll();
        long totalResources = resources.stream().mapToLong(Resource::getQuantity).sum();
        long availableResources = resources.stream().mapToLong(Resource::getAvailableQuantity).sum();
        long activeAllocations = allocationRepository.countByStatus(AllocationStatus.ALLOCATED);

        Map<String, Long> teamsByType = new HashMap<>();
        for (TeamType type : TeamType.values()) {
            teamsByType.put(type.name(), (long) teamRepository.findByType(type).size());
        }

        Map<String, Long> resourcesByCategory = new HashMap<>();
        for (ResourceCategory cat : ResourceCategory.values()) {
            resourcesByCategory.put(cat.name(), resourceRepository.countByCategory(cat));
        }

        return new ResponseStatistics(
                totalTeams,
                availableTeams,
                deployedTeams,
                totalResources,
                availableResources,
                activeAllocations,
                teamsByType,
                resourcesByCategory
        );
    }
}
