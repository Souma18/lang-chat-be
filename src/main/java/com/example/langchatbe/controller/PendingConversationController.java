package com.example.langchatbe.controller;

import com.example.langchatbe.model.PendingConversation;
import com.example.langchatbe.model.dto.CreatePendingRequest;
import com.example.langchatbe.model.dto.PendingUserResponse;
import com.example.langchatbe.model.dto.UpdatePendingRequest;
import com.example.langchatbe.model.dto.OnChatResponse;
import com.example.langchatbe.service.PendingConversationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/chat/pending-conversations")
public class PendingConversationController {

    private final PendingConversationService pendingConversationService;

    public PendingConversationController(PendingConversationService pendingConversationService) {
        this.pendingConversationService = pendingConversationService;
    }

    @PostMapping
    public ResponseEntity<OnChatResponse<PendingConversation>> createPending(@RequestBody CreatePendingRequest request) {
        PendingConversation pendingConversation = pendingConversationService.createOrRefreshPending(request);
        OnChatResponse<PendingConversation> body = new OnChatResponse<>("PENDING_CREATE", pendingConversation);
        return ResponseEntity.ok(body);
    }

    @GetMapping("/incoming")
    public ResponseEntity<OnChatResponse<List<PendingUserResponse>>> getIncoming(@RequestParam("username") String username) {
        List<PendingUserResponse> responses = pendingConversationService.getPendingForUser(username);
        OnChatResponse<List<PendingUserResponse>> body = new OnChatResponse<>("PENDING_INCOMING", responses);
        return ResponseEntity.ok(body);
    }

    @PostMapping("/accept")
    public ResponseEntity<OnChatResponse<Object>> accept(@RequestBody UpdatePendingRequest request) {
        pendingConversationService.acceptOrDelete(request.getFromUsername(), request.getToUsername(), false);
        OnChatResponse<Object> body = new OnChatResponse<>("PENDING_ACCEPT", null);
        return ResponseEntity.ok(body);
    }

    @PostMapping("/delete")
    public ResponseEntity<OnChatResponse<Object>> delete(@RequestBody UpdatePendingRequest request) {
        pendingConversationService.acceptOrDelete(request.getFromUsername(), request.getToUsername(), true);
        OnChatResponse<Object> body = new OnChatResponse<>("PENDING_DELETE", null);
        return ResponseEntity.ok(body);
    }
}

