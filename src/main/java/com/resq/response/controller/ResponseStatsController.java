package com.resq.response.controller;

import com.resq.response.dto.ResponseStatistics;
import com.resq.response.service.ResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/response/statistics")
public class ResponseStatsController {

    private final ResourceService resourceService;

    @Autowired
    public ResponseStatsController(ResourceService resourceService) {
        this.resourceService = resourceService;
    }

    @GetMapping
    public ResponseEntity<ResponseStatistics> getStatistics() {
        ResponseStatistics stats = resourceService.getStatistics();
        return ResponseEntity.ok(stats);
    }
}
