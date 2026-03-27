package com.smhrd.persona.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity
public class Persona_tags {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "persona_tag_id")
    private Integer personaTagId;

    @ManyToOne
    @JoinColumn(name = "persona_id") // personas의 persona_id 참조
    private Persona personas;

    @ManyToOne
    @JoinColumn(name = "hashtag_id") // hashtags의 hashtag_id 참조
    private Hashtags hashtags;
}
