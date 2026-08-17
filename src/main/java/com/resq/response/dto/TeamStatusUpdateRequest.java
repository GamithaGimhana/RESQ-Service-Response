package com.resq.response.dto;

import com.resq.response.model.TeamStatus;
import jakarta.validation.constraints.NotNull;

public class TeamStatusUpdateRequest {

    @NotNull(message = "Status is required")
    private TeamStatus status;

    public TeamStatusUpdateRequest() {
    }

    public TeamStatusUpdateRequest(TeamStatus status) {
        this.status = status;
    }

    public TeamStatus getStatus() {
        return status;
    }

    public void setStatus(TeamStatus status) {
        this.status = status;
    }
}
