package com.smhrd.chat.controller;

import com.smhrd.chat.dto.ChatMessageDto;
import com.smhrd.chat.dto.ChatMsgResponse;
import com.smhrd.chat.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class ChatController {

    private final ChatService chatService;

    @GetMapping("/chat")
    public String chatPage(Model model) {
        List<ChatMsgResponse> responses = chatService.findAll()
                .stream().map(ChatMsgResponse::new).toList();

        // "chatList"라는 이름으로 데이터를 담아서 chat.html로 보냄
        model.addAttribute("chatList", responses);
        return "chat";
    }

    @MessageMapping("/sendMessage")
    @SendTo("/topic/messages")
    public ChatMessageDto sendMessage(ChatMessageDto messageDto){
        chatService.save(messageDto);
        return messageDto;
    }

    @GetMapping("/api/chat")
    @ResponseBody
    public ResponseEntity<List<ChatMsgResponse>> findAllChatMessage(){
        List<ChatMsgResponse> responses = chatService.findRecent10()
                    .stream().map(ChatMsgResponse::new).toList();
        return ResponseEntity.ok().body(responses);
    }
}
