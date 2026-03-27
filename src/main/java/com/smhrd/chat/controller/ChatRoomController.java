package com.smhrd.chat.controller;

import com.smhrd.chat.domain.ChatRoom;
import com.smhrd.common.domain.User;
import com.smhrd.chat.dto.AddChatRoomRequest;
import com.smhrd.chat.service.ChatRoomService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class ChatRoomController {
    private final ChatRoomService service;

    @PostMapping("/api/rooms")
    public ResponseEntity<ChatRoom> creatRoom(@RequestBody AddChatRoomRequest request, HttpSession session){
        User loginUser = (User) session.getAttribute("loginUser");
        if (loginUser == null) {
            throw new RuntimeException("로그인 필요");
        }
        ChatRoom savedChatRoom = service.createRoom(request, loginUser);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedChatRoom);
    }
    // REST: 방 목록 조회
    @GetMapping("/chat/rooms")
    @ResponseBody
    public List<ChatRoom> getRooms(){
        return service.findAllRooms();
    }
}
