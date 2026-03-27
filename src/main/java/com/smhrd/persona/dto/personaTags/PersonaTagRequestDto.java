package com.smhrd.persona.dto.personaTags;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class PersonaTagRequestDto {
    private String systemPrompt;
    private List<Integer> hashtagIds;
    private String personaName;
    private Long userId;
}
