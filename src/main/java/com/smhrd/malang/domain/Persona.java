package com.smhrd.malang.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity(name="personas")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Persona {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "persona_id")
    private Integer personaId;

    @Column(name = "user_id", nullable = false)
    private Integer userId;
    @Column(name = "persona_name", nullable = false, length = 100)
    private String personaName;
    @Column(name = "system_prompt", nullable = false, columnDefinition = "TEXT")
    private String systemPrompt;
    @Column(name = "created_at")
    private LocalDateTime createdAt;
}