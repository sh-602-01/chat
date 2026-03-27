package com.smhrd.chat.service;

import com.smhrd.chat.domain.GuestHashTag;
import com.smhrd.chat.dto.ChatResponse;
import com.smhrd.chat.dto.GuestChatStartRequest;
import com.smhrd.chat.repository.GuestHashtagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class GuestChatService {
    private final GuestHashtagRepository guestHashtagRepository;
    public ChatResponse startChat(GuestChatStartRequest request){
        List<String> resultTags = new ArrayList<>();
        //1. 기본태그
        List<GuestHashTag> defaults = guestHashtagRepository.findByIsDefaultTrue();
        resultTags.addAll(
                defaults.stream().map(GuestHashTag::getName).toList()
        );
        //2. 입력태그
        resultTags.addAll(request.getHashtags());
        //3. 임시 메시지
        String message = "입력한 해시태그" + resultTags + "기반으로 채팅을 시작합니다";
        return  ChatResponse.builder()
                .message(message)
                .hashtags(resultTags)
                .build();
    }
}
