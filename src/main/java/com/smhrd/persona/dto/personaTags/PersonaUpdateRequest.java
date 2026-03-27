package com.smhrd.persona.dto.personaTags;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PersonaUpdateRequest {
    private String personaName;
    private String systemPrompt; // 태그 문자열, 필요 시 분리해서 처리
    private List<Integer> hashtagIds;
}