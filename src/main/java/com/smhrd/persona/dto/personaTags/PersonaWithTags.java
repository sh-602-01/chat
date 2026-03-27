package com.smhrd.persona.dto.personaTags;

import com.smhrd.persona.domain.Persona;
import com.smhrd.persona.domain.Persona_tags;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class PersonaWithTags {

    private Persona personas;
    private List<Persona_tags> tags;
}
