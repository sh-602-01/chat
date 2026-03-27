package com.smhrd.persona.repository;

import com.smhrd.persona.domain.Persona;
import com.smhrd.persona.domain.Persona_tags;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PersonaTagsRepository extends JpaRepository<Persona_tags, Integer> {
    List<Persona_tags> findByPersonas(Persona personas);

    // 데이터베이스에서 personaId로 페르소나 태그 리스트를 조회
}
