package com.smhrd.malang.dto.personaTags;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class PersonaTagRequestDto {
    private List<Integer> hashtagIds;
    private String personaName;
}
