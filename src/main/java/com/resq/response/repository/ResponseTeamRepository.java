package com.resq.response.repository;

import com.resq.response.document.ResponseTeam;
import com.resq.response.model.TeamStatus;
import com.resq.response.model.TeamType;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ResponseTeamRepository extends MongoRepository<ResponseTeam, String> {
    Optional<ResponseTeam> findByTeamCode(String teamCode);
    List<ResponseTeam> findByStatus(TeamStatus status);
    List<ResponseTeam> findByType(TeamType type);
    List<ResponseTeam> findByTypeAndStatus(TeamType type, TeamStatus status);
    long countByStatus(TeamStatus status);
}
