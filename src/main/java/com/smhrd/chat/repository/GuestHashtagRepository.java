package com.smhrd.chat.repository;

import com.smhrd.chat.domain.GuestHashTag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GuestHashtagRepository extends JpaRepository<GuestHashTag, Long> {
   List<GuestHashTag> findByIsDefaultTrue();
}
