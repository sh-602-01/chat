package com.smhrd.chat.service;

import com.smhrd.chat.domain.ChatMessage;
import com.smhrd.chat.dto.ChatMessageDto;
import com.smhrd.chat.repository.ChatMessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ChatService {
    private final ChatMessageRepository repository;

    public ChatMessage save(ChatMessageDto dto){
        ChatMessage message = new ChatMessage();
        message.setOriginal_msg(dto.getMessage());
        message.setFinal_msg(dto.getMessage());
        message.setCorrected_msg(dto.getMessage());
        message.setSender_id(dto.getSenderId().intValue());
        message.setRoom_id(1);
        message.setPersona_id(1);
        return repository.save(message);
    }
}
