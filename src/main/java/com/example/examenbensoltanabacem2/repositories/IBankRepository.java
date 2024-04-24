package com.example.examenbensoltanabacem2.repositories;

import com.example.examenbensoltanabacem2.entities.Bank;
import com.example.examenbensoltanabacem2.entities.Compte;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IBankRepository extends JpaRepository<Bank, Long> {

    Bank findBanksByAgence(String agenceBank);


}
