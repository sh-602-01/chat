package com.smhrd.malang.dto.persona;

import com.smhrd.malang.domain.Persona;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class PersonaCreateRequestDto { //이런성격의 페르소나를 만들어줘 요청하기위해 dto만듦

    private Integer userId; // 어떤 유저가 페르소나를 만들었는지 알기위해
    private String personaName;
    private String systemPrompt; //이 페르소나의 보정 스타일 (해시태그를 조합해서 만들어진 프롬프트인듯?)

    // DTO → Entity 변환
    public Persona toEntity(){
        return Persona.builder()
                .userId(userId)
                .personaName(personaName)
                .systemPrompt(systemPrompt)
                .build();
    }
}