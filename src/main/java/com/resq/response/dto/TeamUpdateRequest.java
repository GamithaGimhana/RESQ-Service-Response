package com.resq.response.dto;

import com.resq.response.model.GeoLocation;
import com.resq.response.model.TeamMember;
import com.resq.response.model.TeamType;

import java.util.List;

public class TeamUpdateRequest {
    private String name;
    private TeamType type;
    private List<TeamMember> members;
    private GeoLocation location;
    private List<String> skills;

    public TeamUpdateRequest() {
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
