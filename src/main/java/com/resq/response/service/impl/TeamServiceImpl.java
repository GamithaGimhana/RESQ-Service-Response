package com.resq.response.service.impl;

import com.resq.response.document.ResponseTeam;
import com.resq.response.dto.TeamCreateRequest;
import com.resq.response.dto.TeamStatusUpdateRequest;
import com.resq.response.dto.TeamUpdateRequest;
import com.resq.response.model.TeamStatus;
import com.resq.response.model.TeamType;
import com.resq.response.repository.ResponseTeamRepository;
import com.resq.response.service.TeamService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
public class TeamServiceImpl implements TeamService {

    private static final Logger log = LoggerFactory.getLogger(TeamServiceImpl.class);
    private final ResponseTeamRepository teamRepository;

    @Autowired
    public TeamServiceImpl(ResponseTeamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }

    private String generateTeamCode() {
        return "RESQ-TEAM-" + UUID.randomUUID().toString().substring(0, 4).toUpperCase();
    }

    @Override
    public ResponseTeam createTeam(TeamCreateRequest request) {
        String code = request.getTeamCode() != null && !request.getTeamCode().trim().isEmpty()
                ? request.getTeamCode().trim()
                : generateTeamCode();

        ResponseTeam team = new ResponseTeam(
                code,
                request.getName(),
                request.getType(),
                request.getStatus() != null ? request.getStatus() : TeamStatus.AVAILABLE,
                request.getMembers(),
                request.getLocation(),
                request.getSkills()
        );

        ResponseTeam saved = teamRepository.save(team);
        log.info("Created rescue team [code={}, name={}, type={}]", saved.getTeamCode(), saved.getName(), saved.getType());
        return saved;
    }

    @Override
    public List<ResponseTeam> getTeams(TeamType type, TeamStatus status) {
        if (type != null && status != null) {
            return teamRepository.findByTypeAndStatus(type, status);
        } else if (type != null) {
            return teamRepository.findByType(type);
        } else if (status != null) {
            return teamRepository.findByStatus(status);
        }
        return teamRepository.findAll();
    }

    @Override
    public ResponseTeam getTeamByIdOrCode(String idOrCode) {
        return teamRepository.findById(idOrCode)
                .or(() -> teamRepository.findByTeamCode(idOrCode))
                .orElseThrow(() -> new NoSuchElementException("Response team not found: " + idOrCode));
    }

    @Override
    public ResponseTeam updateTeam(String id, TeamUpdateRequest request) {
        ResponseTeam team = getTeamByIdOrCode(id);

        if (request.getName() != null) {
            team.setName(request.getName());
        }
        if (request.getType() != null) {
            team.setType(request.getType());
        }
        if (request.getMembers() != null) {
            team.setMembers(request.getMembers());
        }
        if (request.getLocation() != null) {
            team.setLocation(request.getLocation());
        }
        if (request.getSkills() != null) {
            team.setSkills(request.getSkills());
        }

        team.setUpdatedAt(LocalDateTime.now());
        ResponseTeam updated = teamRepository.save(team);
        log.info("Updated rescue team [code={}]", updated.getTeamCode());
        return updated;
    }

    @Override
    public ResponseTeam updateTeamStatus(String id, TeamStatusUpdateRequest request) {
        ResponseTeam team = getTeamByIdOrCode(id);
        team.setStatus(request.getStatus());
        team.setUpdatedAt(LocalDateTime.now());
        ResponseTeam updated = teamRepository.save(team);
        log.info("Updated team status [code={}, status={}]", updated.getTeamCode(), updated.getStatus());
        return updated;
    }

    @Override
    public void deleteTeam(String id) {
        ResponseTeam team = getTeamByIdOrCode(id);
        teamRepository.delete(team);
        log.info("Deleted team [code={}]", team.getTeamCode());
    }
}
