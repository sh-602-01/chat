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
        this.sender_id = message.getUser().getId();        // 🔥 변경
        this.sender_name = message.getUser().getUsername(); // 🔥 핵심
        this.final_msg = message.getFinal_msg();
    }
}
