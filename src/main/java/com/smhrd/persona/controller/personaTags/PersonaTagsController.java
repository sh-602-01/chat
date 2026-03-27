package com.smhrd.persona.controller.personaTags;

import com.smhrd.common.domain.User;
import com.smhrd.persona.domain.Hashtags;
import com.smhrd.persona.domain.Persona;
import com.smhrd.persona.domain.Persona_tags;
import com.smhrd.persona.dto.personaTags.PersonaTagRequestDto;
import com.smhrd.persona.dto.personaTags.PersonaUpdateRequest;
import com.smhrd.persona.dto.personaTags.PersonaWithTags;
import com.smhrd.persona.repository.HashtagsRepository;
import com.smhrd.persona.repository.PersonaTagsRepository;
import com.smhrd.persona.repository.PersonasRepository;
import com.smhrd.persona.service.hashtags.HashtagsService;
import com.smhrd.persona.service.personaTags.PersonaTagsService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class PersonaTagsController {

    private final PersonaTagsService service;
    private final HashtagsService hashtagsService;
    private final PersonasRepository personasRepository;
    private final HashtagsRepository hashtagsRepository;
    private final PersonaTagsRepository personaTagsRepository;

    @GetMapping("/persona_tags/register")
    public String personaRegisterView(@RequestParam(value = "personaId", required = false) Integer personaId, Model model) {
        List<Hashtags> list = hashtagsService.findAll();
        List<String> categories = List.of("ROLE", "TIME", "PLACE", "SITUATION", "RELATION", "TONE", "EMOTION");

        if (personaId != null) {
            // 수정 모드: 기존 데이터 조회
            Persona persona = service.findById(personaId);
            model.addAttribute("persona", persona);

            // 해당 페르소나가 가진 태그 ID 리스트만 뽑아서 전달 (체크박스 활성화용)
            List<Integer> selectedTagIds = persona.getTags().stream()
                    .map(pt -> pt.getHashtags().getHashtagId())
                    .toList();
            model.addAttribute("selectedTagIds", selectedTagIds);
        }

        model.addAttribute("hashtags", list);
        model.addAttribute("categories", categories);
        return "persona_register_view";
    }

    // 수정 실행 (Fetch API용)
    @PutMapping("/persona/{id}")
    @ResponseBody
    public ResponseEntity<String> updatePersona(@PathVariable Integer id, @RequestBody PersonaUpdateRequest dto) {
        try {
            service.update(id, dto);
            return ResponseEntity.ok("Success");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Fail");
        }
    }

    // 태그 조합 저장
    @PostMapping("/persona_tags/create")
    @ResponseBody // 비동기 응답을 위해 필수
    public ResponseEntity<String> createPersonaTags(@RequestBody PersonaTagRequestDto dto, HttpSession session) {
        // 1. @RequestBody 추가: JS의 JSON 데이터를 DTO로 매핑합니다.
        // 2. 반환 타입을 ResponseEntity<String>으로 변경: fetch 요청에 대한 상태코드를 보내기 위함입니다.

        User loginUser = (User) session.getAttribute("loginUser");

        if (loginUser == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인 필요");
        }

        try {
            // 페르소나 마스터 정보 저장
            Persona persona = new Persona();
            persona.setUser(loginUser);
            persona.setPersonaName(dto.getPersonaName());
            persona.setSystemPrompt(dto.getSystemPrompt() != null ? dto.getSystemPrompt() : "기본 system prompt");
            personasRepository.save(persona);

            // 페르소나 상세 태그 저장
            if (dto.getHashtagIds() != null) {
                for (Integer tagId : dto.getHashtagIds()) {
                    Hashtags hashtag = hashtagsRepository.findById(tagId)
                            .orElseThrow(() -> new IllegalArgumentException("해시태그 없음: " + tagId));

                    Persona_tags pt = new Persona_tags();
                    pt.setPersonas(persona);
                    pt.setHashtags(hashtag);
                    personaTagsRepository.save(pt);
                }
            }
            return ResponseEntity.ok("success"); // 성공 시 200 OK 응답
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("fail");
        }
    }

    // 저장된 페르소나 리스트 보기
    @GetMapping("/persona_tags/view")
    public String viewPersonaTags(Model model, HttpSession session){
        User loginUser = (User) session.getAttribute("loginUser");

        if (loginUser == null) {
            throw new IllegalArgumentException("로그인 필요");
        }
        // 유저 1번의 페르소나 조회
        List<Persona> personasList = personasRepository.findByUser(loginUser);

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

    @DeleteMapping("/persona/{id}")
    public ResponseEntity<String> deletePersona(@PathVariable Integer id) {
        boolean deleted = service.deletePersona(id);
        if(deleted){
            return ResponseEntity.ok("삭제 완료");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("삭제 실패");
        }
    }
//    @GetMapping("/persona/{id}")
//    public String editPersonaForm(@PathVariable Integer id, Model model) {
//        Persona persona = service.findById(id);
//        model.addAttribute("persona", persona);
//        return "persona_register_view";
//    }
//    @PostMapping("/persona/{id}")
//    public String updatePersona(@PathVariable Integer id,
//                                @ModelAttribute PersonaUpdateRequest dto) {
//        service.update(id, dto); // 서비스로 수정 처리
//        return "redirect:/my_persona_tags"; // 수정 후 리스트 화면으로
//    }

}
