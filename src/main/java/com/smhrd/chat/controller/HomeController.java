package com.smhrd.chat.controller;

import jakarta.servlet.http.HttpSession;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String rootRedirect() {
        // 루트 접근 시 바로 /index로 리다이렉트
        return "redirect:/index";
    }

    @GetMapping("/index")
    public String index(HttpSession session, Model model) {
        // 로그인 여부 확인
        Object loginUser = session.getAttribute("loginUser");
        if (loginUser != null) {
            model.addAttribute("user", loginUser);
        }
        return "index"; // templates/index.html 렌더링
    }
}
