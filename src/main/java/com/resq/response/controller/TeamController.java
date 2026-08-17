package com.resq.response.controller;

import com.resq.response.document.ResponseTeam;
import com.resq.response.dto.TeamCreateRequest;
import com.resq.response.dto.TeamStatusUpdateRequest;
import com.resq.response.dto.TeamUpdateRequest;
import com.resq.response.model.TeamStatus;
import com.resq.response.model.TeamType;
import com.resq.response.service.TeamService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/response/teams")
public class TeamController {

    private final TeamService teamService;

    @Autowired
    public TeamController(TeamService teamService) {
        this.teamService = teamService;
    }

    @PostMapping
    public ResponseEntity<ResponseTeam> createTeam(@Valid @RequestBody TeamCreateRequest request) {
        ResponseTeam created = teamService.createTeam(request);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<ResponseTeam>> getTeams(
            @RequestParam(required = false) TeamType type,
            @RequestParam(required = false) TeamStatus status) {
        List<ResponseTeam> teams = teamService.getTeams(type, status);
        return ResponseEntity.ok(teams);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseTeam> getTeamById(@PathVariable String id) {
        ResponseTeam team = teamService.getTeamByIdOrCode(id);
        return ResponseEntity.ok(team);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseTeam> updateTeam(
            @PathVariable String id,
            @Valid @RequestBody TeamUpdateRequest request) {
        ResponseTeam updated = teamService.updateTeam(id, request);
        return ResponseEntity.ok(updated);
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<ResponseTeam> updateTeamStatus(
            @PathVariable String id,
            @Valid @RequestBody TeamStatusUpdateRequest request) {
        ResponseTeam updated = teamService.updateTeamStatus(id, request);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTeam(@PathVariable String id) {
        teamService.deleteTeam(id);
        return ResponseEntity.noContent().build();
    }
}
