package com.resq.response.document;

import com.resq.response.model.GeoLocation;
import com.resq.response.model.TeamMember;
import com.resq.response.model.TeamStatus;
import com.resq.response.model.TeamType;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Document(collection = "response_teams")
public class ResponseTeam {

    @Id
    private String id;

    @Indexed(unique = true)
    private String teamCode;

    private String name;

    @Indexed
    private TeamType type;

    @Indexed
    private TeamStatus status = TeamStatus.AVAILABLE;

    private List<TeamMember> members = new ArrayList<>();

    private GeoLocation location;

    private List<String> skills = new ArrayList<>();

    private LocalDateTime createdAt = LocalDateTime.now();

    private LocalDateTime updatedAt = LocalDateTime.now();

    public ResponseTeam() {
    }

    public ResponseTeam(String teamCode, String name, TeamType type, TeamStatus status, List<TeamMember> members, GeoLocation location, List<String> skills) {
        this.teamCode = teamCode;
        this.name = name;
        this.type = type;
        this.status = status;
        this.members = members != null ? members : new ArrayList<>();
        this.location = location;
        this.skills = skills != null ? skills : new ArrayList<>();
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
