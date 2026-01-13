package com.example.langchatbe.model;

import jakarta.persistence.*;

@Entity
@Table(name = "group_conversation_settings", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"group_name"})
})
public class GroupConversationSetting {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "group_name", nullable = false, length = 255)
    private String groupName;

    @Column(name = "theme_id", nullable = false, length = 50)
    private String themeId = "DEFAULT";

    @Column(name = "owner", nullable = true, length = 255)
    private String owner;

    @Column(name = "last_changed_by", nullable = true, length = 255)
    private String lastChangedBy;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getThemeId() {
        return themeId;
    }

    public void setThemeId(String themeId) {
        this.themeId = themeId;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getLastChangedBy() {
        return lastChangedBy;
    }

    public void setLastChangedBy(String lastChangedBy) {
        this.lastChangedBy = lastChangedBy;
    }
}
