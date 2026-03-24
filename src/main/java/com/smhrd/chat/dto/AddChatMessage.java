package com.smhrd.chat.dto;

import com.smhrd.chat.domain.ChatMessage;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class AddChatMessage {

    private int room_id;
    private int sender_id;
    private String original_msg;
    private String corrected_msg;
    private String final_msg;
    private String explanation;
    private int persona_id;
}
