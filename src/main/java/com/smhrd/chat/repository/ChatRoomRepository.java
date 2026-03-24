package com.smhrd.chat.repository;

import com.smhrd.chat.domain.ChatRoom;
import com.smhrd.chat.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {
    List<ChatRoom> findByCreator(User creator);
}
