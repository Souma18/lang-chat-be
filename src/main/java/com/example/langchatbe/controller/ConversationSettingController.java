package com.example.langchatbe.controller;

import com.example.langchatbe.model.ConversationSetting;
import com.example.langchatbe.model.GroupConversationSetting;
import com.example.langchatbe.model.dto.OnChatResponse;
import com.example.langchatbe.model.dto.SetThemeRequest;
import com.example.langchatbe.model.dto.SetGroupThemeRequest;
import com.example.langchatbe.service.ConversationSettingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/chat/settings")
public class ConversationSettingController {

    private final ConversationSettingService settingService;

    public ConversationSettingController(ConversationSettingService settingService) {
        this.settingService = settingService;
    }

    @GetMapping("/theme")
    public ResponseEntity<OnChatResponse<String>> getTheme(
            @RequestParam("user1") String user1,
            @RequestParam("user2") String user2
    ) {
        String currentTheme = settingService.getTheme(user1, user2);
        return ResponseEntity.ok(new OnChatResponse<>("GET_THEME_SUCCESS", currentTheme));
    }

    @PostMapping("/theme")
    public ResponseEntity<OnChatResponse<String>> setTheme(@RequestBody SetThemeRequest request) {
        String updatedTheme = settingService.updateTheme(request.getUserOne(), request.getUserTwo(), request.getThemeId());
        return ResponseEntity.ok(new OnChatResponse<>("SET_THEME_SUCCESS", updatedTheme));
    }

    @GetMapping("/group")
    public ResponseEntity<OnChatResponse<GroupConversationSetting>> getGroupTheme(
            @RequestParam("groupName") String groupName
    ) {
        GroupConversationSetting setting = settingService.getGroupTheme(groupName);
        return ResponseEntity.ok(new OnChatResponse<>("GET_GROUP_THEME_SUCCESS", setting));
    }

    @PostMapping("/group")
    public ResponseEntity<OnChatResponse<GroupConversationSetting>> updateGroupTheme(@RequestBody SetGroupThemeRequest request) {
        GroupConversationSetting updatedSetting = settingService.saveOrUpdateGroupTheme(
                request.getGroupName(), 
                request.getUsername(), 
                request.getThemeId()
        );
        return ResponseEntity.ok(new OnChatResponse<>("SET_GROUP_THEME_SUCCESS", updatedSetting));
    }
}
