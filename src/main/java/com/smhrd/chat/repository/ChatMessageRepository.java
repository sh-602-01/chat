package com.smhrd.chat.repository;

import com.smhrd.chat.domain.ChatMessage;
import com.smhrd.chat.domain.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {
    List<ChatMessage> findByChatRoomOrderByMsgIdDesc(ChatRoom room);
}