package com.example.langchatbe.service;

import com.example.langchatbe.model.ConversationSetting;
import com.example.langchatbe.model.GroupConversationSetting;
import com.example.langchatbe.repository.ConversationSettingRepository;
import com.example.langchatbe.repository.GroupConversationSettingRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class ConversationSettingService {

    private final ConversationSettingRepository repository;
    private final GroupConversationSettingRepository groupRepository;

    public ConversationSettingService(ConversationSettingRepository repository, GroupConversationSettingRepository groupRepository) {
        this.repository = repository;
        this.groupRepository = groupRepository;
    }

    @Transactional(readOnly = true)
    public String getTheme(String user1, String user2) {
        String u1 = user1.compareTo(user2) < 0 ? user1 : user2;
        String u2 = user1.compareTo(user2) < 0 ? user2 : user1;

        return repository.findByUserOneAndUserTwo(u1, u2)
                .map(ConversationSetting::getThemeId)
                .orElse("DEFAULT");
    }

    @Transactional
    public String updateTheme(String user1, String user2, String themeId) {
        String u1 = user1.compareTo(user2) < 0 ? user1 : user2;
        String u2 = user1.compareTo(user2) < 0 ? user2 : user1;

        ConversationSetting setting = repository.findByUserOneAndUserTwo(u1, u2)
                .orElseGet(() -> {
                    ConversationSetting newSetting = new ConversationSetting();
                    newSetting.setUserOne(u1);
                    newSetting.setUserTwo(u2);
                    return newSetting;
                });
        
        setting.setThemeId(themeId);
        repository.save(setting);
        
        return setting.getThemeId();
    }

    // --- GROUP CONVERSATION ---

    @Transactional(readOnly = true)
    public GroupConversationSetting getGroupTheme(String groupName) {
        return groupRepository.findByGroupName(groupName).orElse(null);
    }

    @Transactional
    public GroupConversationSetting saveOrUpdateGroupTheme(String groupName, String username, String themeId) {
        Optional<GroupConversationSetting> existingGroup = groupRepository.findByGroupName(groupName);

        if (existingGroup.isPresent()) {
            GroupConversationSetting setting = existingGroup.get();
            setting.setThemeId(themeId);
            setting.setLastChangedBy(username);
            return groupRepository.save(setting);
        } else {
            GroupConversationSetting newSetting = new GroupConversationSetting();
            newSetting.setGroupName(groupName);
            newSetting.setThemeId(themeId);
            newSetting.setLastChangedBy(username);
            // Owner is left as null intentionally for now
            return groupRepository.save(newSetting);
        }
    }
}
