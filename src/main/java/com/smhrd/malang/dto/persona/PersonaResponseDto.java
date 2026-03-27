package com.smhrd.malang.dto.persona;

import com.smhrd.malang.domain.Persona;
import lombok.Getter;

@Getter
public class PersonaResponseDto { //응답용 dto(서버->사용자) //엔터티->dto변환용이라 롬복x직접 생성자씀

    private Integer personaId; //페르소나생성요청 결과로 생성된 페르소나의 식별자를 보내줌
    private String personaName;
    private String systemPrompt; //사용자한테는 노출안함

    public PersonaResponseDto(Persona persona){
        this.personaId = persona.getPersonaId();
        this.personaName = persona.getPersonaName();
        this.systemPrompt = persona.getSystemPrompt();
    }

}
