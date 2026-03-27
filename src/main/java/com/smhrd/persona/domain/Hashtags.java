package com.smhrd.persona.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Entity
public class Hashtags {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "hashtag_id")
    private Integer hashtagId;

    @Column(name = "hashtag_name", unique = true)
    private String hashtagName;

    @Enumerated(EnumType.STRING) // DB에 enum(열거형)을 이름 그대로 문자열로 저장 / .ORDINAL의 경우 순서로 저장
    @Column(name = "category")
    private CategoryType category;

    public enum CategoryType{
        ROLE,TIME,PLACE,SITUATION,RELATION,TONE,EMOTION
    }
}
