package com.smhrd.chat.controller;

import com.smhrd.chat.domain.User;
import com.smhrd.chat.repository.UserRepository;
import com.smhrd.chat.service.ChatRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import jakarta.servlet.http.HttpSession;

@RequiredArgsConstructor
@Controller
public class ChatRoomViewController {

    private final ChatRoomService service;
    private final UserRepository userRepository;

    @GetMapping("/create_room")
    public String createRoomPage() {
        return "create_room";
    }

    @GetMapping("/room_list")
    public String roomList(Model model, HttpSession session) {
        User loginUser = (User) session.getAttribute("loginUser");
        if (loginUser == null) {
            throw new RuntimeException("로그인 필요");
        }
        model.addAttribute("rooms", service.findMyRooms(loginUser));
        return "room_list";
    }
}