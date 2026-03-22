package com.smhrd.chat.domain;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@EntityListeners(AuditingEntityListener.class)
@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class ChatMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false)
    private Long msg_id;

    @Column(name="room_id")
    private int room_id;

    @Column(name="sender_id")
    private int sender_id;

    @Column(name="original_msg")
    private String original_msg;

    private String corrected_msg;
    private String final_msg;
    private String explanation;
    private int persona_id;

    @CreatedDate //날짜생성
    @Column(name="created_at")
    private LocalDateTime createdAt; //게시물이 추가된 시간(자동)

}
