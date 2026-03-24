package com.smhrd.chat.domain;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@EntityListeners(AuditingEntityListener.class)
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class ChatRoom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "room_id", nullable = false)
    private Long roomId;
    @Column(name = "room_name")
    private String roomName;
    @Column(name = "room_type")
    private int roomType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "create_by") // DB 컬럼명을 create_by로 지정
    private User creator; // 변수명은 의미를 담아 creator 또는 user로 설정

    @CreatedDate //날짜생성
    @Column(name="created_at")
    private LocalDateTime createdAt; //게시물이 추가된 시간(자동)

    @Column(name = "last_message")
    private String lastMessage;

    @Column(name = "last_message_time")
    private LocalDateTime lastMessageTime;
}
