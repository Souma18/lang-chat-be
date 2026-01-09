package com.example.langchatbe.repository;

import com.example.langchatbe.model.PendingConversation;
import com.example.langchatbe.model.PendingStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PendingConversationRepository extends JpaRepository<PendingConversation, Long> {

    List<PendingConversation> findByToUsernameAndStatus(String toUsername, PendingStatus status);

    Optional<PendingConversation> findByFromUsernameAndToUsernameAndStatus(String fromUsername,
                                                                           String toUsername,
                                                                           PendingStatus status);
}

