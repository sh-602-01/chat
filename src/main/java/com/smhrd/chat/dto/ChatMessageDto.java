package com.smhrd.chat.dto;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
public class ChatMessageDto {
    private Long senderId;
    private String sender;
    private String message;
}
// 주석달아서 커밋해볼께요
