package com.smhrd.chat.controller;

import com.smhrd.chat.dto.ChatResponse;
import com.smhrd.chat.dto.GuestChatStartRequest;
import com.smhrd.chat.service.GuestChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/guest/chat")
public class GuestChatController {
    private final GuestChatService guestChatService;
    @PostMapping("/start")
    public ChatResponse start(@RequestBody GuestChatStartRequest request){
        return guestChatService.startChat(request);
    }
}
