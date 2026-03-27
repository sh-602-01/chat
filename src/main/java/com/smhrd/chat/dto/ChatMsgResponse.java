package com.smhrd.chat.dto;

import com.smhrd.chat.domain.ChatMessage;
import lombok.Getter;

@Getter
public class ChatMsgResponse {
    private Long msgId;
    private Long sender_id;
    private String sender_name;
    private String final_msg;

    public ChatMsgResponse(ChatMessage message){
        this.msgId = message.getMsgId();

        if (message.getUser() != null) {
            this.sender_id = message.getUser().getId();
            this.sender_name = message.getUser().getUserId();
        } else {
            // 🔥 guest 처리
            this.sender_id = -1L; // 또는 null
            this.sender_name = "guest";
        }

        this.final_msg = message.getFinal_msg();
    }
}
