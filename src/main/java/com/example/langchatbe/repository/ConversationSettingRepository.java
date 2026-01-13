package com.example.langchatbe.repository;

import com.example.langchatbe.model.ConversationSetting;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface ConversationSettingRepository extends JpaRepository<ConversationSetting, Long> {
    
    Optional<ConversationSetting> findByUserOneAndUserTwo(String userOne, String userTwo);
    
}
