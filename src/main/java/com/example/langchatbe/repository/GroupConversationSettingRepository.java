package com.example.langchatbe.repository;

import com.example.langchatbe.model.GroupConversationSetting;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface GroupConversationSettingRepository extends JpaRepository<GroupConversationSetting, Long> {
    Optional<GroupConversationSetting> findByGroupName(String groupName);
}
