package com.smhrd.admin.controller;

import com.smhrd.admin.service.AdminService;
import com.smhrd.chat.dto.CreateUserRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@Controller
@RequestMapping("/admin") // 앞부분 공통 엔드포인터 처리
public class AdminController {

    private final AdminService adminService;

    // 유저 등록 페이지 이동
    @GetMapping("/users/save")
    public String saveForm() {
        return "user_save_admin";
    }

    // 유저 등록 실행
    @PostMapping("/users/save")
    public String save(@ModelAttribute CreateUserRequest req) {
        adminService.saveUser(req);
        return "redirect:/admin/users"; // 저장 후 목록으로 리다이렉트
    }

    // 리스트 조회
    @GetMapping("/users")
     public String userList(Model model){
        model.addAttribute("users", adminService.findAllUsers());
        return "user_list_admin";
    }

    // 유저 삭제
    @ResponseBody
    @DeleteMapping("/users/{id}")
    public String delete(@PathVariable Long id){
        adminService.deleteUser(id);
        return "ok";
    }
}
