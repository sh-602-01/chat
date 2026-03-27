package com.smhrd.chat.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ChatRoomListResponse {
    private Long roomId;
    private String roomName;
    private String lastMessage;
    private String lastMessageTime;
}
