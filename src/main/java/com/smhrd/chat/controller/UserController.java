package com.smhrd.chat.controller;

import com.smhrd.chat.domain.User;
import com.smhrd.chat.dto.CreateUserRequest;
import com.smhrd.chat.dto.LoginRequest;
import com.smhrd.chat.service.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor
@Controller
@RequestMapping("/api")
public class UserController {
    private final UserService service;

    @GetMapping("/join")
    public String join(){
        return "join";
    }
    @GetMapping("/login")
    public String chat(){
        return "login";
    }

    @PostMapping("/users")
    public String  signup(CreateUserRequest request){
        service.signup(request); //DB에 추가
        return "redirect:/api/login";
    }

    //로그인
    @PostMapping("/login_user")
    public String login(LoginRequest request, HttpSession session){
        try {
            User user = service.login(request);
            session.setAttribute("loginUser", user);
            return "redirect:/api/login";
        } catch (IllegalArgumentException e) {
            // 로그인 실패
            return "redirect:/api/login?error=true";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session){
        session.invalidate(); //세션에 저장된 사용자 정보 삭제
        return "redirect:/api/login";
    }
}
