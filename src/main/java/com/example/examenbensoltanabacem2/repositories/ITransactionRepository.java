package com.example.examenbensoltanabacem2.repositories;

import com.example.examenbensoltanabacem2.entities.Compte;
import com.example.examenbensoltanabacem2.entities.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface ITransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findTransactionsByDateTransaction(LocalDate date);

    @Query("SELECT t FROM Transaction t WHERE t.compteExpediteur.idCompte IN (SELECT c.idCompte FROM Compte c Join Bank b WHERE b.idBank = :idBank)")
    List<Transaction> getAllTransactionByBankId(Long idBank);


}






