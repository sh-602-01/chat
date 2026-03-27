package com.smhrd.chat.controller;

import com.smhrd.chat.domain.ChatRoom;
import com.smhrd.common.domain.User;
import com.smhrd.chat.dto.ChatMessageDto;
import com.smhrd.chat.dto.ChatMsgResponse;
import com.smhrd.chat.service.ChatRoomService;
import com.smhrd.chat.service.ChatService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class ChatController {

    private final ChatService chatService;
    private final ChatRoomService chatRoomService;

    // WebSocket: 메시지 전송
    @MessageMapping("/sendMessage/{roomId}")
    @SendTo("/topic/messages/{roomId}")
    public ChatMsgResponse sendMessage(@DestinationVariable Long roomId, ChatMessageDto messageDto){
        messageDto.setRoomId(roomId);
        var savedMessage = chatService.save(messageDto);
        return new ChatMsgResponse(savedMessage);
    }

    // 1. [페이지 이동용] 사용자가 채팅방에 처음 들어갈 때 (HTML을 보여줌)
    @GetMapping("/chat/{roomId}")
    public String chatPage(@PathVariable Long roomId, Model model, HttpSession session) {

        ChatRoom room = chatRoomService.findById(roomId);

        Object loginUser = session.getAttribute("loginUser");

        String userId;
        if (loginUser != null) {
            userId = ((User) loginUser).getId().toString();
        } else {
            userId = "guest_" + java.util.UUID.randomUUID().toString().substring(0, 8);
        }

        model.addAttribute("roomId", roomId);
        model.addAttribute("roomName", room.getRoomName());
        model.addAttribute("userId", userId);

        return "chat_new";
    }

    // 2. [데이터 조회용] JS의 fetch가 호출하는 곳 (JSON 데이터를 줌)
    @GetMapping("/api/chat/{roomId}")
    @ResponseBody // 핵심: 이걸 붙여야 "chat"이라는 글자가 아니라 실제 데이터가 나갑니다!
    public List<ChatMsgResponse> getChatData(@PathVariable Long roomId) {
        return chatService.findByRoom(roomId)
                .stream()
                .map(ChatMsgResponse::new)
                .toList();
    }
}