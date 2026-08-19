package com.resq.response.service;

import com.resq.response.document.ResponseTeam;
import com.resq.response.dto.TeamCreateRequest;
import com.resq.response.dto.TeamStatusUpdateRequest;
import com.resq.response.model.TeamMember;
import com.resq.response.model.TeamStatus;
import com.resq.response.model.TeamType;
import com.resq.response.repository.ResponseTeamRepository;
import com.resq.response.service.impl.TeamServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TeamServiceTests {

    @Mock
    private ResponseTeamRepository teamRepository;

    private TeamService teamService;

    @BeforeEach
    void setUp() {
        teamService = new TeamServiceImpl(teamRepository);
    }

    @Test
    void testCreateTeam() {
        TeamCreateRequest request = new TeamCreateRequest();
        request.setTeamCode("RESQ-TEAM-001");
        request.setName("Panadura Flood Response");
        request.setType(TeamType.FLOOD_RESPONSE);
        request.setStatus(TeamStatus.AVAILABLE);
        request.setSkills(Arrays.asList("WATER_RESCUE", "FIRST_AID"));
        request.setMembers(Arrays.asList(new TeamMember("Officer Silva", "LEADER", "+94771112233")));

        when(teamRepository.save(any(ResponseTeam.class))).thenAnswer(inv -> inv.getArgument(0));

        ResponseTeam created = teamService.createTeam(request);

        assertNotNull(created);
        assertEquals("RESQ-TEAM-001", created.getTeamCode());
        assertEquals("Panadura Flood Response", created.getName());
        assertEquals(TeamStatus.AVAILABLE, created.getStatus());
        verify(teamRepository).save(any(ResponseTeam.class));
    }

    @Test
    void testUpdateTeamStatus() {
        ResponseTeam team = new ResponseTeam();
        team.setId("team-id-1");
        team.setTeamCode("RESQ-TEAM-001");
        team.setStatus(TeamStatus.AVAILABLE);

        when(teamRepository.findById("team-id-1")).thenReturn(Optional.of(team));
        when(teamRepository.save(any(ResponseTeam.class))).thenAnswer(inv -> inv.getArgument(0));

        TeamStatusUpdateRequest updateRequest = new TeamStatusUpdateRequest(TeamStatus.DEPLOYED);
        ResponseTeam updated = teamService.updateTeamStatus("team-id-1", updateRequest);

        assertNotNull(updated);
        assertEquals(TeamStatus.DEPLOYED, updated.getStatus());
    }
}
