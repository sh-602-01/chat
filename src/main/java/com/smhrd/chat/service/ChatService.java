package com.smhrd.chat.service;

import com.smhrd.chat.domain.ChatMessage;
import com.smhrd.chat.domain.ChatRoom;
import com.smhrd.common.domain.User;
import com.smhrd.chat.dto.ChatMessageDto;
import com.smhrd.chat.repository.ChatMessageRepository;
import com.smhrd.chat.repository.ChatRoomRepository;
import com.smhrd.chat.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ChatService {

    private final ChatMessageRepository chatMessageRepository;
    private final UserRepository userRepository;
    private final ChatRoomService chatRoomService;
    private final ChatRoomRepository chatRoomRepository;

    public ChatMessage save(ChatMessageDto dto){

        ChatRoom room = chatRoomService.findById(dto.getRoomId());
        User user = null;

        if (dto.getSenderId() != null && !dto.getSenderId().startsWith("guest_")) {
            user = userRepository.findById(Long.parseLong(dto.getSenderId()))
                    .orElse(null);
        }

        ChatMessage message = ChatMessage.builder()
                .original_msg(dto.getMessage())
                .corrected_msg(dto.getMessage())
                .final_msg(dto.getMessage())
                .explanation(dto.getExplanation())
                .persona_id(dto.getPersonaId())
                .user(user)
                .chatRoom(room)
                .build();

        ChatMessage saved = chatMessageRepository.save(message);
        room.setLastMessage(saved.getFinal_msg()); // 또는 getOriginal_msg()
        room.setLastMessageTime(saved.getCreatedAt());
        chatRoomRepository.save(room);
        return saved;
    }

    public List<ChatMessage> findByRoom(Long roomId){
        ChatRoom room = chatRoomService.findById(roomId);
        return chatMessageRepository.findByChatRoomOrderByMsgIdDesc(room);
    }

    public List<ChatMessage> findRecent10(){
        Pageable topTen = PageRequest.of(0, 10, Sort.by("msgId").descending());
        return chatMessageRepository.findAll(topTen).getContent();
    }
}