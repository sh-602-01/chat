package com.smhrd.malang.repository;

import com.smhrd.malang.domain.Hashtags;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HashtagsRepository extends JpaRepository<Hashtags, Integer> {
    boolean existsByHashtagName(String hashtagName);
}
