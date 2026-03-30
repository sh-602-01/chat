package com.smhrd.admin.service;

// 반드시 아래의 org.springframework.data 패키지에서 가져와야 합니다.
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.smhrd.admin.dto.ChatMessageListResponse;
import com.smhrd.admin.repository.ChatLogRepository;
import com.smhrd.chat.domain.ChatMessage;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class ChatLogService {

    private final ChatLogRepository chatLogRepository;

    /**
     * 관리자용: 페이징 처리된 채팅 메시지 목록 조회
     */
    public Page<ChatMessageListResponse> findAllPaged(Pageable pageable) {
        // chatLogRepository.findAll(pageable)은 Page<ChatMessage>를 반환합니다.
        return chatLogRepository.findAll(pageable)
                .map(this::convertToDto);
    }

    /**
     * 특정 메시지 상세 조회
     */
    public ChatMessageListResponse findByMsgId(Long msgId) {
        ChatMessage message = chatLogRepository.findById(msgId)
                .orElseThrow(() -> new RuntimeException("해당 메시지를 찾을 수 없습니다. ID: " + msgId));
        return convertToDto(message);
    }

    /**
     * Entity -> DTO 변환 로직
     */
    private ChatMessageListResponse convertToDto(ChatMessage chatMessage) {
    return ChatMessageListResponse.builder()
            .msgId(chatMessage.getMsgId())
            .roomId(chatMessage.getChatRoom() != null ? chatMessage.getChatRoom().getRoomId() : null)
            .senderId(chatMessage.getUser() != null ? chatMessage.getUser().getUserId() : "Unknown")
            .originalMsg(chatMessage.getOriginal_msg()) // Entity의 getter 확인
            .finalMsg(chatMessage.getFinal_msg())
            .explanation(chatMessage.getExplanation())
            .personaId(chatMessage.getPersona_id())
            .createdAt(chatMessage.getCreatedAt())
            .build();
}

   /**
 * 메시지 삭제 로직
 */
@Transactional
public void deleteMessage(Long msgId) {
    if (!chatLogRepository.existsById(msgId)) {
        throw new RuntimeException("삭제하려는 메시지가 존재하지 않습니다. ID: " + msgId);
    }
    chatLogRepository.deleteById(msgId);
}
}