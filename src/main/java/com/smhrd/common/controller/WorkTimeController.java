package com.smhrd.common.controller;

import com.smhrd.chat.repository.UserRepository;
import com.smhrd.common.domain.User;
import com.smhrd.common.dto.WorkTimeDto;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/user")
public class WorkTimeController {
    private final UserRepository userRepository;

    @PostMapping("/worktime")
    public ResponseEntity<String> setWorkTime(@RequestBody WorkTimeDto dto,
                                              HttpSession session) {

        User user = (User) session.getAttribute("loginUser");

        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("로그인이 필요합니다.");
        }

        user.setWorkStartTime(LocalTime.parse(dto.getStartTime()));
        user.setWorkEndTime(LocalTime.parse(dto.getEndTime()));

        userRepository.save(user);

        return ResponseEntity.ok("근무시간 저장 완료");
    }

    @GetMapping("/worktime")
    public ResponseEntity<?> getWorkTime(HttpSession session) {

        User user = (User) session.getAttribute("loginUser");

        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("로그인이 필요합니다.");
        }

        Map<String, String> result = new HashMap<>();
        result.put("startTime", user.getWorkStartTime().toString());
        result.put("endTime", user.getWorkEndTime().toString());

        return ResponseEntity.ok(result);
    }
}
