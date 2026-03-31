package com.smhrd.admin.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.smhrd.chat.domain.ChatMessages;

public interface ChatLogRepository extends JpaRepository<ChatMessages, Long> {

    
}
