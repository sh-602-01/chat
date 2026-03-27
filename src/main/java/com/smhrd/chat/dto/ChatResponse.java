package com.smhrd.chat.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;
@Getter
@Builder
public class ChatResponse {
    private String message;
    private List<String> hashtags;
}
