package com.example.langchatbe.service;

import com.example.langchatbe.model.PendingConversation;
import com.example.langchatbe.model.PendingStatus;
import com.example.langchatbe.model.dto.CreatePendingRequest;
import com.example.langchatbe.model.dto.PendingUserResponse;
import com.example.langchatbe.repository.PendingConversationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PendingConversationService {

    private final PendingConversationRepository pendingConversationRepository;

    public PendingConversationService(PendingConversationRepository pendingConversationRepository) {
        this.pendingConversationRepository = pendingConversationRepository;
    }

    @Transactional
    public PendingConversation createOrRefreshPending(CreatePendingRequest request) {
        String fromUser = request.getFromUsername();
        String toUser = request.getToUsername();

        if (fromUser.equals(toUser)) {
            throw new IllegalArgumentException("Cannot send request to yourself");
        }

        // Check if already friends bidirectional (A->B or B->A with status ACCEPTED)
        boolean existsForward = pendingConversationRepository.existsByFromUsernameAndToUsernameAndStatus(fromUser, toUser, PendingStatus.ACCEPTED);
        boolean existsBackward = pendingConversationRepository.existsByFromUsernameAndToUsernameAndStatus(toUser, fromUser, PendingStatus.ACCEPTED);

        if (existsForward || existsBackward) {
            throw new IllegalStateException("Conversation already exists");
        }

        return pendingConversationRepository
                .findByFromUsernameAndToUsernameAndStatus(fromUser, toUser, PendingStatus.PENDING)
                .map(existing -> pendingConversationRepository.save(existing))
                .orElseGet(() -> {
                    PendingConversation pc = new PendingConversation();
                    pc.setFromUsername(fromUser);
                    pc.setToUsername(toUser);
                    pc.setStatus(PendingStatus.PENDING);
                    return pendingConversationRepository.save(pc);
                });
    }

    @Transactional(readOnly = true)
    public List<PendingConversation> getPendingForUser(String toUsername) {
        return pendingConversationRepository.findByToUsernameAndStatus(toUsername, PendingStatus.PENDING);
    }

    @Transactional
    public void acceptRequest(String fromUsername, String toUsername) {
        PendingConversation pc = pendingConversationRepository
                .findByFromUsernameAndToUsernameAndStatus(fromUsername, toUsername, PendingStatus.PENDING)
                .orElseThrow(() -> new IllegalArgumentException("Pending conversation not found"));
        
        pc.setStatus(PendingStatus.ACCEPTED);
        pendingConversationRepository.save(pc);
    }

    @Transactional
    public void deleteRequest(String fromUsername, String toUsername) {
        pendingConversationRepository
                .findByFromUsernameAndToUsernameAndStatus(fromUsername, toUsername, PendingStatus.PENDING)
                .ifPresent(pendingConversationRepository::delete);
    }
}

