package com.example.examenbensoltanabacem2.services;

import com.example.examenbensoltanabacem2.entities.Bank;
import com.example.examenbensoltanabacem2.entities.Compte;
import com.example.examenbensoltanabacem2.entities.Transaction;

import java.util.List;

public interface IService {
    Compte addCompte(Compte compte);
    Compte getCompte(Long id);
    List<Compte> getAlComptes();



    Bank ajouterBank(Bank bank);

    Compte ajouterCompteEtAffecterAAgence(Compte compte, String agenceBank);

    String ajouterVirement(Transaction transaction);

    String ajouterRetrait(Transaction transaction);
    String ajouterVersement(Transaction transaction);

    void getAllTransactionByDate();

    List<Transaction>getAllTransactionByBankId(Long idBank);

}
