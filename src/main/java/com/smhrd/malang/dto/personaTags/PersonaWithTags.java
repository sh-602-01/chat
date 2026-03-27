package com.smhrd.malang.dto.personaTags;

import com.smhrd.malang.domain.Persona;
import com.smhrd.malang.domain.Persona_tags;
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
