package com.resq.response.dto;

import com.resq.response.model.GeoLocation;
import com.resq.response.model.TeamMember;
import com.resq.response.model.TeamStatus;
import com.resq.response.model.TeamType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.ArrayList;
import java.util.List;

public class TeamCreateRequest {

    private String teamCode;

    @NotBlank(message = "Team name is required")
    private String name;

    @NotNull(message = "Team type is required")
    private TeamType type;

    private TeamStatus status = TeamStatus.AVAILABLE;

    private List<TeamMember> members = new ArrayList<>();

    private GeoLocation location;

    private List<String> skills = new ArrayList<>();

    public TeamCreateRequest() {
    }

    public String getTeamCode() {
        return teamCode;
    }

    public void setTeamCode(String teamCode) {
        this.teamCode = teamCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public TeamType getType() {
        return type;
    }

    public void setType(TeamType type) {
        this.type = type;
    }

    public TeamStatus getStatus() {
        return status;
    }

    public void setStatus(TeamStatus status) {
        this.status = status;
    }

    public List<TeamMember> getMembers() {
        return members;
    }

    public void setMembers(List<TeamMember> members) {
        this.members = members;
    }

    public GeoLocation getLocation() {
        return location;
    }

    public void setLocation(GeoLocation location) {
        this.location = location;
    }

    public List<String> getSkills() {
        return skills;
    }

    public void setSkills(List<String> skills) {
        this.skills = skills;
    }
}
