package com.smhrd.chat.controller;

import com.smhrd.chat.dto.AddChatMessage;
import com.smhrd.chat.dto.ChatMessageDto;
import com.smhrd.chat.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@RequiredArgsConstructor
@Controller
public class ChatController {

    private final ChatService chatService;

    @GetMapping("/chat")
    public String chat(){
        return "chat";
    }

    @MessageMapping("/sendMessage")
    @SendTo("/topic/messages")
    public ChatMessageDto sendMessage(ChatMessageDto messageDto){
        chatService.save(messageDto);
        return messageDto;
    }
}
