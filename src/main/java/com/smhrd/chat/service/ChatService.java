package com.smhrd.chat.service;

import com.smhrd.chat.domain.ChatMessage;
import com.smhrd.chat.domain.User;
import com.smhrd.chat.dto.ChatMessageDto;
import com.smhrd.chat.repository.ChatMessageRepository;
import com.smhrd.chat.repository.UserRepository;
import lombok.RequiredArgsConstructor;
// 중요: 아래 Pageable과 PageRequest를 임포트해야 합니다.
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ChatService {

    private final ChatMessageRepository repository;
    private final UserRepository userRepository;

    public ChatMessage save(ChatMessageDto dto){

        User user = userRepository.findById(dto.getSenderId())
                .orElseThrow(() -> new RuntimeException("유저 없음"));

        ChatMessage message = new ChatMessage();
        message.setOriginal_msg(dto.getMessage());
        message.setFinal_msg(dto.getMessage());
        message.setCorrected_msg(dto.getMessage());

        message.setUser(user); // 🔥 핵심 변경

        message.setRoom_id(1);
        message.setPersona_id(1);

        return repository.save(message);
    }

    public List<ChatMessage> findAll(){
        return repository.findAll();
    }

    public List<ChatMessage> findRecent10() {
        // 1. Spring Data Domain의 Pageable을 사용합니다.
        Pageable topTen = PageRequest.of(0, 10, Sort.by("msgId").descending());

        // 2. 클래스명이 아닌 주입받은 'repository' 객체를 사용합니다.
        // 3. 엔티티의 ID 필드명이 'id'인지 'chat_msg_id'인지 확인 후 Sort.by()에 넣으세요.
        return repository.findAll(topTen).getContent();
    }
}