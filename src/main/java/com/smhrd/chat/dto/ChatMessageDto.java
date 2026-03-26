package com.smhrd.chat.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ChatMessageDto {
    private Long roomId;
    private String senderId;
    private String message;      // original_msg
    private String explanation;  // optional
    private int personaId;
}