package com.resq.response.service;

import com.resq.response.document.ResponseTeam;
import com.resq.response.dto.TeamCreateRequest;
import com.resq.response.dto.TeamStatusUpdateRequest;
import com.resq.response.dto.TeamUpdateRequest;
import com.resq.response.model.TeamStatus;
import com.resq.response.model.TeamType;

import java.util.List;

public interface TeamService {
    ResponseTeam createTeam(TeamCreateRequest request);
    List<ResponseTeam> getTeams(TeamType type, TeamStatus status);
    ResponseTeam getTeamByIdOrCode(String idOrCode);
    ResponseTeam updateTeam(String id, TeamUpdateRequest request);
    ResponseTeam updateTeamStatus(String id, TeamStatusUpdateRequest request);
    void deleteTeam(String id);
}
