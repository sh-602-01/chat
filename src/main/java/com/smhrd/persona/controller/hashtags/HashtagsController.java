package com.smhrd.persona.controller.hashtags;

import com.smhrd.persona.domain.Hashtags;
import com.smhrd.persona.repository.HashtagsRepository;
import com.smhrd.persona.repository.PersonaTagsRepository;
import com.smhrd.persona.repository.PersonasRepository;
import com.smhrd.persona.service.hashtags.HashtagsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class HashtagsController {

    private final HashtagsService hashtagsService;


    // 해시태그 전체 리스트 불러오기
    @GetMapping("/hashtags/hashtag_list")
    public String hashList(Model model){
        List<Hashtags> list = hashtagsService.findAll();
        model.addAttribute("hashtags", list);
        return "hashtag_list";
    }

    // 새로운 해시태그등록 화면 불러오기
    @GetMapping("/hashtags/register")
    public String hashtagRegisterView() {
        return "hashtag_register_view";
    }

    // 해시태그 등록 하고 list 로 보냄
    @PostMapping("/hashtags/save")
    public String save(Hashtags hashtags, Model model){
        try{
            hashtagsService.saveHashtags(hashtags);
        } catch (IllegalArgumentException e){
            model.addAttribute("errorMessage", e.getMessage());
            model.addAttribute("hashtags", hashtagsService.findAll());
            return "hashtag_list";
        }
        return "redirect:/hashtags/hashtag_list";
    }

    // 해시태그 삭제
    @PostMapping("/hashtags/delete")
    public String delete(Integer hashtagId){
        hashtagsService.delete(hashtagId);
        return "redirect:/hashtags/hashtag_list";
    }
}
