package com.smhrd.malang.controller.personaTags;

import com.smhrd.malang.domain.Hashtags;
import com.smhrd.malang.domain.Persona;
import com.smhrd.malang.domain.Persona_tags;
import com.smhrd.malang.dto.personaTags.PersonaTagRequestDto;
import com.smhrd.malang.dto.personaTags.PersonaWithTags;
import com.smhrd.malang.repository.HashtagsRepository;
import com.smhrd.malang.repository.PersonaTagsRepository;
import com.smhrd.malang.repository.PersonasRepository;
import com.smhrd.malang.service.hashtags.HashtagsService;
import com.smhrd.malang.service.personaTags.PersonaTagsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class PersonaTagsController {

    private final PersonaTagsService service;
    private final HashtagsService hashtagsService;
    private final PersonasRepository personasRepository;
    private final HashtagsRepository hashtagsRepository;
    private final PersonaTagsRepository personaTagsRepository;


    // 페르소나 등록 화면
    @GetMapping("/persona_tags/register")
    public String personaRegisterView(Model model) {
        List<Hashtags> list = hashtagsService.findAll();
        model.addAttribute("hashtags", list);
        return "persona_register_view";
    }

    // 태그 조합 저장
    @PostMapping("/persona_tags/create")
    public String createPersonaTags(PersonaTagRequestDto dto){
        if (dto.getHashtagIds() == null || dto.getHashtagIds().isEmpty()){
            return "redirect:/persona_tags/register";
        }

        Persona persona = new Persona();
        persona.setUserId(1);
        persona.setPersonaName(dto.getPersonaName());
        persona.setSystemPrompt("기본 system prompt");
        personasRepository.save(persona);

        for (Integer tagId : dto.getHashtagIds()) {
            Hashtags hashtag = hashtagsRepository.findById(tagId)
                    .orElseThrow(() -> new IllegalArgumentException("해시태그 없음: " + tagId));

            Persona_tags pt = new Persona_tags();
            pt.setPersonas(persona);
            pt.setHashtags(hashtag);
            personaTagsRepository.save(pt);
        }
        return "redirect:/persona_tags/view"; // 저장 후 목록으로 이동
    }

    // 저장된 페르소나 리스트 보기
    @GetMapping("/persona_tags/view")
    public String viewPersonaTags(Model model){
        // 유저 1번의 페르소나 조회
        List<Persona> personasList = personasRepository.findByUserId(1);

        // 페르소나별 태그 매핑 로직
        List<PersonaWithTags> personaWithTagsList = personasList.stream().map(persona -> {
            List<Persona_tags> tags = personaTagsRepository.findByPersonas(persona);
            return new PersonaWithTags(persona, tags);
        }).toList();

        model.addAttribute("personaWithTagsList", personaWithTagsList);
        return "my_persona_tags";
    }

    // 페르소나에 맞는 해시태그 조회
    @PostMapping ("/persona_tags/search")
    public String showPersonaTags(@RequestParam("personaId") Integer personaId, Model model){

        // 입력받은 ID로 persona_tags 테이블 조회
        List<Persona_tags> list = service.findByPersonasPersonaId(personaId);

        // 결과 리스트 전달
        model.addAttribute("personaTags", list);

        // 검색 결과
        model.addAttribute("searchedId", personaId);

        model.addAttribute("personas", service.findAllPersonas());

        return "personaTagsView";
    }
}
