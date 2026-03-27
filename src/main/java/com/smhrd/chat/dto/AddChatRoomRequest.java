package com.smhrd.chat.dto;

import com.smhrd.chat.domain.ChatRoom;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AddChatRoomRequest {
    private String roomName;
    private int roomType;
    private Long creatorId;
    public ChatRoom toEntity(){
        return ChatRoom.builder()
                .roomName(roomName)
                .roomType(roomType)
                .build();
    }
}
