package com.resq.response.repository;

import com.resq.response.document.Resource;
import com.resq.response.model.ResourceCategory;
import com.resq.response.model.ResourceStatus;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ResourceRepository extends MongoRepository<Resource, String> {
    Optional<Resource> findByResourceCode(String resourceCode);
    List<Resource> findByCategory(ResourceCategory category);
    List<Resource> findByStatus(ResourceStatus status);
    List<Resource> findByCategoryAndStatus(ResourceCategory category, ResourceStatus status);
    long countByCategory(ResourceCategory category);
    long countByStatus(ResourceStatus status);
}
