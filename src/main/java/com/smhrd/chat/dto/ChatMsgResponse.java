package com.smhrd.chat.dto;

import com.smhrd.chat.domain.ChatMessage;
import lombok.Getter;

@Getter
public class ChatMsgResponse {
    private String final_msg;
    public ChatMsgResponse(ChatMessage message){
        this.final_msg = message.getFinal_msg();
    }
}
