package com.smhrd.chat.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalTime;

@NoArgsConstructor
@Setter
@Getter
public class CreateUserRequest {
    private String userId;
    private String password;
    private String userName;
    private LocalTime workStartTime;
    private LocalTime workEndTime;
}
