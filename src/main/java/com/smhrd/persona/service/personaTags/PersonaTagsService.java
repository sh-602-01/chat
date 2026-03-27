package com.smhrd.persona.service.personaTags;

import com.smhrd.persona.domain.Hashtags;
import com.smhrd.persona.domain.Persona;
import com.smhrd.persona.domain.Persona_tags;
import com.smhrd.persona.dto.personaTags.PersonaUpdateRequest;
import com.smhrd.persona.repository.HashtagsRepository;
import com.smhrd.persona.repository.PersonaTagsRepository;
import com.smhrd.persona.repository.PersonasRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;


@RequiredArgsConstructor
@Service
public class PersonaTagsService {

    private final PersonaTagsRepository repository;
    private final PersonasRepository personasRepository;
    private final HashtagsRepository hashtagsRepository;


    // 조회한 페르소나 조회
    public List<Persona_tags> findByPersonasPersonaId(Integer personaId){
        Persona persona = personasRepository.findById(personaId)
                .orElseThrow(() -> new IllegalArgumentException("페르소나 없음: " + personaId));
        return repository.findByPersonas(persona);
    }

    // 모든 페르소나 조회
    public List<Persona> findAllPersonas(){
        return personasRepository.findAll();
    }

    public boolean deletePersona(Integer id){
        try {
            personasRepository.deleteById(id);
            return true;
        } catch(Exception e){
            return false;
        }
    }

    public Persona findById(Integer id) {
        return personasRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 페르소나가 없습니다. id=" + id));
    }

    @Transactional
    public void update(Integer id, PersonaUpdateRequest dto) {
        // 1️⃣ 기존 페르소나 조회
        Persona persona = personasRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 페르소나가 없습니다. id=" + id));

        // 2️⃣ 페르소나 이름 및 시스템 프롬프트 수정
        persona.setPersonaName(dto.getPersonaName());
        persona.setSystemPrompt(dto.getSystemPrompt());

        // 3️⃣ 기존 태그 삭제
        List<Persona_tags> existingTags = repository.findByPersonas(persona);
        repository.deleteAll(existingTags);

        // 4️⃣ 새로운 태그 추가
        if (dto.getHashtagIds() != null) {
            for (Integer tagId : dto.getHashtagIds()) {
                Hashtags tag = hashtagsRepository.findById(tagId)
                        .orElseThrow(() -> new IllegalArgumentException("해시태그가 없습니다. id=" + tagId));

                Persona_tags pt = new Persona_tags();
                pt.setPersonas(persona);
                pt.setHashtags(tag);

                repository.save(pt);
            }
        }

        // 5️⃣ 페르소나 저장 (태그는 별도로 관리되므로 필요 없지만, 안전하게 save)
        personasRepository.save(persona);
    }
}
