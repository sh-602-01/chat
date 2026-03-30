package com.smhrd.admin.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.smhrd.admin.dto.ChatMessageListResponse;
import com.smhrd.admin.service.ChatLogService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/admin/chat")
@RequiredArgsConstructor
public class AdminChatController {

    private final ChatLogService chatLogService;

    @GetMapping("/list")
    public String getChatLogList(
            @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable,
            Model model) {
        
        Page<ChatMessageListResponse> chatPage = chatLogService.findAllPaged(pageable);
        
        model.addAttribute("chatPage", chatPage);
        // 타임리프에서 페이지네이션을 쉽게 그리기 위한 변수들
        model.addAttribute("currentPage", chatPage.getNumber());
        model.addAttribute("totalPages", chatPage.getTotalPages());
        
        return "admin/chat_list";
    }
    @PostMapping("/delete/{msgId}")
    public String deleteMessage(@PathVariable("msgId") Long msgId) {
        chatLogService.deleteMessage(msgId);
        return "redirect:/admin/chat/list"; // 삭제 후 다시 리스트로 이동
    }
}