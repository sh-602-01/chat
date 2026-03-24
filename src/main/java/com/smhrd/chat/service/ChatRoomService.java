package com.smhrd.chat.service;

import com.smhrd.chat.domain.ChatRoom;
import com.smhrd.chat.domain.User;
import com.smhrd.chat.dto.AddChatRoomRequest;
import com.smhrd.chat.dto.ChatRoomListResponse;
import com.smhrd.chat.repository.ChatRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ChatRoomService {
    private final ChatRoomRepository chatRoomRepository;
    public ChatRoom createRoom(AddChatRoomRequest request, User loginUser) {
        ChatRoom room = request.toEntity(); // 기본 정보 세팅
        room.setCreator(loginUser); // ✅ 여기서 넣는다
        return chatRoomRepository.save(room);
    }

    public List<ChatRoom> findAllRooms(){
        return chatRoomRepository.findAll();
    }

    public ChatRoom findById(Long roomId){
        return chatRoomRepository.findById(roomId)
                .orElseThrow(() -> new RuntimeException("방 없음"));
    }
    // 내가 만든 방만 보임
    public List<ChatRoom> findMyRooms(User user){
        return chatRoomRepository.findByCreator(user);
    }

    public List<ChatRoomListResponse> getRoomList(User user){
        List<ChatRoom> rooms = chatRoomRepository.findAll();
        return rooms.stream()
                .map(room -> new ChatRoomListResponse(
                        room.getRoomId(),
                        room.getRoomName(),
                        room.getLastMessage(),
                        formatTime(room.getLastMessageTime()) // 🔥 포맷 적용
                ))
                .toList();
    }

    // 🔥 시간 포맷 메서드
    private String formatTime(LocalDateTime time){
        if(time == null) return "";

        LocalDateTime now = LocalDateTime.now();

        // 오늘이면
        if(time.toLocalDate().isEqual(now.toLocalDate())){
            return time.format(DateTimeFormatter.ofPattern("a h:mm")); // 오전/오후
        }

        // 오늘 아니면
        return time.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
    }

}