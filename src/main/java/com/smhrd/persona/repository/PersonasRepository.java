package com.smhrd.persona.repository;

import com.smhrd.common.domain.User;
import com.smhrd.persona.domain.Persona;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PersonasRepository extends JpaRepository<Persona, Integer> {
    List<Persona> findByUser(User user);
}
