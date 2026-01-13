package com.example.langchatbe.model;

import jakarta.persistence.*;

@Entity
@Table(name = "conversation_settings", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"user_one", "user_two"})
})
public class ConversationSetting {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_one", nullable = false, length = 255)
    private String userOne;

    @Column(name = "user_two", nullable = false, length = 255)
    private String userTwo;

    @Column(name = "theme_id", nullable = false, length = 50)
    private String themeId = "DEFAULT";

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserOne() {
        return userOne;
    }

    public void setUserOne(String userOne) {
        this.userOne = userOne;
    }

    public String getUserTwo() {
        return userTwo;
    }

    public void setUserTwo(String userTwo) {
        this.userTwo = userTwo;
    }

    public String getThemeId() {
        return themeId;
    }

    public void setThemeId(String themeId) {
        this.themeId = themeId;
    }
}
