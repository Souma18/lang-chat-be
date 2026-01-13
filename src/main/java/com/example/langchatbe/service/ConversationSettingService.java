package com.example.langchatbe.service;

import com.example.langchatbe.model.ConversationSetting;
import com.example.langchatbe.repository.ConversationSettingRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ConversationSettingService {

    private final ConversationSettingRepository repository;

    public ConversationSettingService(ConversationSettingRepository repository) {
        this.repository = repository;
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
}
