package com.smhrd.chat.repository;

import com.smhrd.chat.domain.ChatMessages;
import com.smhrd.chat.domain.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatMessageRepository extends JpaRepository<ChatMessages, Long> {
    List<ChatMessages> findByChatRoomOrderByMsgIdDesc(ChatRoom room);
}