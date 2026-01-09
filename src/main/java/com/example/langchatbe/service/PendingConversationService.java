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
        return pendingConversationRepository
                .findByFromUsernameAndToUsernameAndStatus(
                        request.getFromUsername(),
                        request.getToUsername(),
                        PendingStatus.PENDING
                )
                .map(existing -> {
                    // chỉ cần touch updatedAt (đã được handle bởi @PreUpdate)
                    return pendingConversationRepository.save(existing);
                })
                .orElseGet(() -> {
                    PendingConversation pc = new PendingConversation();
                    pc.setFromUsername(request.getFromUsername());
                    pc.setToUsername(request.getToUsername());
                    pc.setStatus(PendingStatus.PENDING);
                    return pendingConversationRepository.save(pc);
                });
    }

    @Transactional(readOnly = true)
    public List<PendingUserResponse> getPendingForUser(String toUsername) {
        List<PendingConversation> conversations =
                pendingConversationRepository.findByToUsernameAndStatus(toUsername, PendingStatus.PENDING);

        return conversations.stream()
                .map(pc -> new PendingUserResponse(
                        pc.getFromUsername(),
                        pc.getStatus().name(),
                        pc.getCreatedAt()
                ))
                .collect(Collectors.toList());
    }

    @Transactional
    public void acceptOrDelete(String fromUsername, String toUsername, boolean delete) {
        pendingConversationRepository
                .findByFromUsernameAndToUsernameAndStatus(
                        fromUsername,
                        toUsername,
                        PendingStatus.PENDING
                )
                .ifPresent(pc -> {
                    if (delete) {
                        pendingConversationRepository.delete(pc);
                    } else {
                        pc.setStatus(PendingStatus.ACCEPTED);
                        pendingConversationRepository.save(pc);
                    }
                });
    }
}

