package com.smhrd.persona.service.hashtags;

import com.smhrd.chat.repository.UserRepository;
import com.smhrd.common.domain.User;
import com.smhrd.persona.domain.Hashtags;
import com.smhrd.persona.domain.Persona;
import com.smhrd.persona.domain.Persona_tags;
import com.smhrd.persona.dto.personaTags.PersonaTagRequestDto;
import com.smhrd.persona.repository.HashtagsRepository;
import com.smhrd.persona.repository.PersonaTagsRepository;
import com.smhrd.persona.repository.PersonasRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class HashtagsService {

    private final HashtagsRepository hashtagsRepository;
    private final PersonaTagsRepository personaTagsRepository;
    private final PersonasRepository personasRepository;
    private final UserRepository userRepository;

    // 해시태그 저장
    public void saveHashtags(Hashtags hashtags){
        if(hashtagsRepository.existsByHashtagName(hashtags.getHashtagName())){
            throw new IllegalArgumentException("존재하는 해시태그입니다.");
        }
        hashtagsRepository.save(hashtags);
    }

    // 전체 해시 태그 조회
    public List<Hashtags> findAll(){
        List<Hashtags> list = hashtagsRepository.findAll();
        return list;
    }

    // 해시태그 삭제
    public void delete(Integer hashtagId){
        hashtagsRepository.deleteById(hashtagId);
    }

    // 해시태그 조합 저장
    @Transactional // 저장하다가 에러나면 취소해주는 라이브러리
    public void savePersonaTags(PersonaTagRequestDto dto){

        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("유저 없음"));
        // Personas 생성
        Persona persona = new Persona();
        persona.setUser(user); // not null이라 임의로 넣음
        persona.setPersonaName(dto.getPersonaName());
        personasRepository.save(persona);

        // 선택한 해시태그들 페르소나에 연결
        for (Integer id : dto.getHashtagIds()){

            Hashtags hashtag = hashtagsRepository.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException("해시태그 없음"));

            Persona_tags pt = new Persona_tags();
            pt.setPersonas(persona);
            pt.setHashtags(hashtag);

            personaTagsRepository.save(pt);
        }

        }
    }

