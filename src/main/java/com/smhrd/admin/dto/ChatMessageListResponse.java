package com.smhrd.admin.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Getter
@Builder
public class ChatMessageListResponse {    
    private Long msgId;   
    private Long roomId;
    private String senderId;
    private String originalMsg; // orignal_msg -> originalMsg (카멜케이스 권장)
    private String finalMsg;    // final_msg -> finalMsg
    private String explanation;
    private Integer personaId;  // int -> Integer (null 방지)
    private LocalDateTime createdAt;
}