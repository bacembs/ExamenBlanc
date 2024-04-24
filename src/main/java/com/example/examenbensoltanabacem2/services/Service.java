package com.example.examenbensoltanabacem2.services;

import com.example.examenbensoltanabacem2.entities.*;
import com.example.examenbensoltanabacem2.repositories.IBankRepository;
import com.example.examenbensoltanabacem2.repositories.ICompteRepository;
import com.example.examenbensoltanabacem2.repositories.ITransactionRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
@EnableScheduling
@org.springframework.stereotype.Service
@RequiredArgsConstructor
public class Service implements IService {
    private final ICompteRepository repositoryCompte;
    private final IBankRepository repositoryBank;
    private final ITransactionRepository repositoryTransaction;
    @Override
    public Compte addCompte(Compte compte) {
        return repositoryCompte.save(compte);
    }

    @Override
    public Compte getCompte(Long id) {
        return repositoryCompte.findById(id).orElse(null);
    }

    @Override
    public List<Compte> getAlComptes() {
        return (List)repositoryCompte.findAll();
    }




    @Override
    public Bank ajouterBank(Bank bank) {
        return repositoryBank.save(bank);
    }

    @Override
    public Compte ajouterCompteEtAffecterAAgence(Compte compte, String agenceBank) {
            Bank bank = repositoryBank.findBanksByAgence(agenceBank);
            bank.getComptes().add(compte);

        return repositoryCompte.save(compte);
    }


    @Transactional
    @Override
    public String ajouterVirement(Transaction transaction) {
        if (transaction.getTypeTransaction()== TypeTransaction.Virement
                &&transaction.getCompteExpediteur().getTypeCompteC()==TypeCompte.Epargne){
                return "On ne peut pas faire un virement à partir d'un compte épargne";
        }

        if (transaction.getTypeTransaction()==TypeTransaction.Virement
                &&transaction.getCompteExpediteur().getTypeCompteC()== TypeCompte.Courant
                &&transaction.getMontant()>transaction.getCompteExpediteur().getSolde()){
                return "One ne peut pas faire un virement : Solde insuffisant";
        }
        Compte compteExpediteur = repositoryCompte.findById(transaction.getCompteExpediteur().getIdCompte()).orElse(null);
        Compte compteDestinataire = repositoryCompte.findById(transaction.getCompteDestinataire().getIdCompte()).orElse(null);

        compteExpediteur.setSolde(compteExpediteur.getSolde()-transaction.getMontant());
        compteDestinataire.setSolde(compteDestinataire.getSolde()+transaction.getMontant());
        transaction.setDateTransaction(LocalDate.now());

        repositoryCompte.save(compteExpediteur);
        repositoryCompte.save(compteDestinataire);
        repositoryTransaction.save(transaction);

        return "Virement de "+ transaction.getMontant() + "DT de compte "
                +compteExpediteur.getCode()+ "vers le compte "
                +compteDestinataire.getCode()+ "approuvé avec succès";
    }

    @Override
    public String ajouterRetrait(Transaction transaction) {
        Compte compteExpediteur = repositoryCompte.findById(transaction.getCompteExpediteur().getIdCompte()).orElse(null);

        if (transaction.getTypeTransaction()== TypeTransaction.Retrait
                &&compteExpediteur.getSolde()<transaction.getMontant()+2){
            return "On ne peut pas faire un retrait: Solde Insuffisant"+transaction.getMontant()+"/"+compteExpediteur.getSolde();
        }
        transaction.setDateTransaction(LocalDate.now());

        compteExpediteur.setSolde(compteExpediteur.getSolde()-transaction.getMontant()-2);

        repositoryCompte.save(compteExpediteur);
        repositoryTransaction.save(transaction);

        return "RETRAIT de"+transaction.getMontant()+" DT de compte "+compteExpediteur.getCode()+" approuvé avec succès.";
    }

    @Override
    public String ajouterVersement(Transaction transaction) {
        Compte compteDestinataire = repositoryCompte.findById(transaction.getCompteDestinataire().getIdCompte()).orElse(null);
        if (transaction.getTypeTransaction()== TypeTransaction.Versement
                &&compteDestinataire.getTypeCompteC()==TypeCompte.Courant){
            transaction.setDateTransaction(LocalDate.now());
            compteDestinataire.setSolde(compteDestinataire.getSolde()+transaction.getMontant()-2);

            repositoryCompte.save(compteDestinataire);
            repositoryTransaction.save(transaction);
            return "Versement de"+transaction.getMontant()+" DT de compte "+compteDestinataire.getCode()+" approuvé avec succès.";
        }
        transaction.setDateTransaction(LocalDate.now());
        compteDestinataire.setSolde(compteDestinataire.getSolde()+transaction.getMontant());
        repositoryCompte.save(compteDestinataire);
        repositoryTransaction.save(transaction);
        return "Versement de"+transaction.getMontant()+" DT de compte "+compteDestinataire.getCode()+" approuvé avec succès sans frais.";
    }

    @Scheduled(fixedRate = 30000)
    @Override
    public void getAllTransactionByDate() {
        LocalDate today = LocalDate.now();
        List<Transaction> transactions = repositoryTransaction.findTransactionsByDateTransaction(today);
        for (Transaction transaction : transactions) {
            System.out.println(transaction);
        }
    }

    @Override
    public List<Transaction> getAllTransactionByBankId(Long idBank) {
        return repositoryTransaction.getAllTransactionByBankId(idBank);
    }


}
