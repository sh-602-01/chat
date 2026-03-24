package com.smhrd.chat.dto;

import com.smhrd.chat.domain.ChatRoom;
import com.smhrd.chat.domain.User;
import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
