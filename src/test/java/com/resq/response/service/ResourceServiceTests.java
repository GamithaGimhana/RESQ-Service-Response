package com.resq.response.service;

import com.resq.response.document.Resource;
import com.resq.response.document.ResourceAllocation;
import com.resq.response.dto.AllocationCreateRequest;
import com.resq.response.dto.ResourceCreateRequest;
import com.resq.response.dto.ResponseStatistics;
import com.resq.response.model.AllocationStatus;
import com.resq.response.model.ResourceCategory;
import com.resq.response.model.ResourceStatus;
import com.resq.response.model.TeamStatus;
import com.resq.response.repository.ResourceAllocationRepository;
import com.resq.response.repository.ResourceRepository;
import com.resq.response.repository.ResponseTeamRepository;
import com.resq.response.service.impl.ResourceServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ResourceServiceTests {

    @Mock
    private ResourceRepository resourceRepository;

    @Mock
    private ResourceAllocationRepository allocationRepository;

    @Mock
    private ResponseTeamRepository teamRepository;

    private ResourceService resourceService;

    @BeforeEach
    void setUp() {
        resourceService = new ResourceServiceImpl(resourceRepository, allocationRepository, teamRepository);
    }

    @Test
    void testCreateResource() {
        ResourceCreateRequest request = new ResourceCreateRequest();
        request.setResourceCode("BOAT-001");
        request.setName("Rescue Inflatable Boat");
        request.setCategory(ResourceCategory.VEHICLE);
        request.setQuantity(5);
        request.setLocation("Panadura Base");

        when(resourceRepository.save(any(Resource.class))).thenAnswer(inv -> inv.getArgument(0));

        Resource created = resourceService.createResource(request);

        assertNotNull(created);
        assertEquals("BOAT-001", created.getResourceCode());
        assertEquals(5, created.getQuantity());
        assertEquals(5, created.getAvailableQuantity());
    }

    @Test
    void testAllocateResourceSuccess() {
        Resource resource = new Resource("BOAT-001", "Rescue Boat", ResourceCategory.VEHICLE, 4, 4, ResourceStatus.AVAILABLE, "Base A");
        resource.setId("res-1");

        when(resourceRepository.findById("res-1")).thenReturn(Optional.of(resource));
        when(resourceRepository.save(any(Resource.class))).thenAnswer(inv -> inv.getArgument(0));
        when(allocationRepository.save(any(ResourceAllocation.class))).thenAnswer(inv -> {
            ResourceAllocation alloc = inv.getArgument(0);
            alloc.setId("alloc-1");
            return alloc;
        });

        AllocationCreateRequest req = new AllocationCreateRequest("INC-001", "res-1", 2, "dispatcher@resq.gov");
        ResourceAllocation allocation = resourceService.allocateResource(req);

        assertNotNull(allocation);
        assertEquals(2, allocation.getAllocatedQuantity());
        assertEquals(2, resource.getAvailableQuantity());
        assertEquals(AllocationStatus.ALLOCATED, allocation.getStatus());
    }

    @Test
    void testAllocateResourceInsufficientInventory() {
        Resource resource = new Resource("BOAT-001", "Rescue Boat", ResourceCategory.VEHICLE, 4, 1, ResourceStatus.AVAILABLE, "Base A");
        resource.setId("res-1");

        when(resourceRepository.findById("res-1")).thenReturn(Optional.of(resource));

        AllocationCreateRequest req = new AllocationCreateRequest("INC-001", "res-1", 3, "dispatcher@resq.gov");
        assertThrows(IllegalArgumentException.class, () -> resourceService.allocateResource(req));
    }

    @Test
    void testReleaseAllocation() {
        Resource resource = new Resource("BOAT-001", "Rescue Boat", ResourceCategory.VEHICLE, 4, 2, ResourceStatus.AVAILABLE, "Base A");
        resource.setId("res-1");

        ResourceAllocation allocation = new ResourceAllocation("INC-001", "res-1", "BOAT-001", "Rescue Boat", 2, "dispatcher@resq.gov");
        allocation.setId("alloc-1");

        when(allocationRepository.findById("alloc-1")).thenReturn(Optional.of(allocation));
        when(allocationRepository.save(any(ResourceAllocation.class))).thenAnswer(inv -> inv.getArgument(0));
        when(resourceRepository.findById("res-1")).thenReturn(Optional.of(resource));
        when(resourceRepository.save(any(Resource.class))).thenAnswer(inv -> inv.getArgument(0));

        ResourceAllocation released = resourceService.releaseAllocation("alloc-1");

        assertEquals(AllocationStatus.RELEASED, released.getStatus());
        assertEquals(4, resource.getAvailableQuantity());
    }

    @Test
    void testGetStatistics() {
        when(teamRepository.count()).thenReturn(3L);
        when(teamRepository.countByStatus(TeamStatus.AVAILABLE)).thenReturn(2L);
        when(teamRepository.countByStatus(TeamStatus.DEPLOYED)).thenReturn(1L);

        Resource r1 = new Resource("R1", "Boat", ResourceCategory.VEHICLE, 5, 3, ResourceStatus.AVAILABLE, "Loc A");
        Resource r2 = new Resource("R2", "Ambulance", ResourceCategory.VEHICLE, 3, 2, ResourceStatus.AVAILABLE, "Loc B");
        when(resourceRepository.findAll()).thenReturn(Arrays.asList(r1, r2));
        when(allocationRepository.countByStatus(AllocationStatus.ALLOCATED)).thenReturn(3L);

        ResponseStatistics stats = resourceService.getStatistics();

        assertNotNull(stats);
        assertEquals(3L, stats.getTotalTeams());
        assertEquals(2L, stats.getAvailableTeams());
        assertEquals(8L, stats.getTotalResources());
        assertEquals(5L, stats.getAvailableResources());
        assertEquals(3L, stats.getActiveAllocations());
    }
}
