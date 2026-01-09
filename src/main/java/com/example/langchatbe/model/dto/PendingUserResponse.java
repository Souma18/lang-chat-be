package com.example.langchatbe.model.dto;

import java.time.Instant;

public class PendingUserResponse {

    private String username;
    private String status;
    private Instant createdAt;

    public PendingUserResponse() {
    }

    public PendingUserResponse(String username, String status, Instant createdAt) {
        this.username = username;
        this.status = status;
        this.createdAt = createdAt;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }
}

