package com.smhrd.persona.repository;

import com.smhrd.persona.domain.Hashtags;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface HashtagsRepository extends JpaRepository<Hashtags, Integer> {
    boolean existsByHashtagName(String hashtagName);
    Optional<Hashtags> findByHashtagName(String hashtagName);
}
