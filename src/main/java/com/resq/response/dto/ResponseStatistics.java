package com.resq.response.dto;

import java.util.Map;

public class ResponseStatistics {
    private long totalTeams;
    private long availableTeams;
    private long deployedTeams;
    private long totalResources;
    private long availableResources;
    private long activeAllocations;
    private Map<String, Long> teamsByType;
    private Map<String, Long> resourcesByCategory;

    public ResponseStatistics() {
    }

    public ResponseStatistics(long totalTeams, long availableTeams, long deployedTeams, long totalResources, long availableResources, long activeAllocations, Map<String, Long> teamsByType, Map<String, Long> resourcesByCategory) {
        this.totalTeams = totalTeams;
        this.availableTeams = availableTeams;
        this.deployedTeams = deployedTeams;
        this.totalResources = totalResources;
        this.availableResources = availableResources;
        this.activeAllocations = activeAllocations;
        this.teamsByType = teamsByType;
        this.resourcesByCategory = resourcesByCategory;
    }

    public long getTotalTeams() {
        return totalTeams;
    }

    public void setTotalTeams(long totalTeams) {
        this.totalTeams = totalTeams;
    }

    public long getAvailableTeams() {
        return availableTeams;
    }

    public void setAvailableTeams(long availableTeams) {
        this.availableTeams = availableTeams;
    }

    public long getDeployedTeams() {
        return deployedTeams;
    }

    public void setDeployedTeams(long deployedTeams) {
        this.deployedTeams = deployedTeams;
    }

    public long getTotalResources() {
        return totalResources;
    }

    public void setTotalResources(long totalResources) {
        this.totalResources = totalResources;
    }

    public long getAvailableResources() {
        return availableResources;
    }

    public void setAvailableResources(long availableResources) {
        this.availableResources = availableResources;
    }

    public long getActiveAllocations() {
        return activeAllocations;
    }

    public void setActiveAllocations(long activeAllocations) {
        this.activeAllocations = activeAllocations;
    }

    public Map<String, Long> getTeamsByType() {
        return teamsByType;
    }

    public void setTeamsByType(Map<String, Long> teamsByType) {
        this.teamsByType = teamsByType;
    }

    public Map<String, Long> getResourcesByCategory() {
        return resourcesByCategory;
    }

    public void setResourcesByCategory(Map<String, Long> resourcesByCategory) {
        this.resourcesByCategory = resourcesByCategory;
    }
}
