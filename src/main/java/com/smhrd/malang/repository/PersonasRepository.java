package com.smhrd.malang.repository;

import com.smhrd.malang.domain.Persona;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PersonasRepository extends JpaRepository<Persona, Integer> {
    List<Persona> findByUserId(Integer userId);
}
