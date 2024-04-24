package com.example.examenbensoltanabacem2.repositories;

import com.example.examenbensoltanabacem2.entities.Compte;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ICompteRepository extends JpaRepository<Compte, Long> {
}
