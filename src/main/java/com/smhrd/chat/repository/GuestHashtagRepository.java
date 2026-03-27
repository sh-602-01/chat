package com.smhrd.chat.repository;

import com.smhrd.chat.domain.GuestHashTag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface GuestHashtagRepository extends JpaRepository<GuestHashTag, Long> {
    Optional<GuestHashTag> findByname(String name);
    List<GuestHashTag> findByIsDefaultTrue();
}
