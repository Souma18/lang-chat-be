package com.example.langchatbe.model.dto;

public class OnChatResponse<T> {

    private String action = "onchat";
    private OnChatResponseData<T> data;

    public OnChatResponse() {
    }

    public OnChatResponse(String event, T payload) {
        this.data = new OnChatResponseData<>(event, payload);
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public OnChatResponseData<T> getData() {
        return data;
    }

    public void setData(OnChatResponseData<T> data) {
        this.data = data;
    }
}

