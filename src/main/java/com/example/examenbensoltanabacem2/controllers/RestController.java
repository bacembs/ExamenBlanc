package com.example.examenbensoltanabacem2.controllers;

import com.example.examenbensoltanabacem2.entities.Bank;
import com.example.examenbensoltanabacem2.entities.Compte;
import com.example.examenbensoltanabacem2.entities.Transaction;
import com.example.examenbensoltanabacem2.services.IService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@org.springframework.web.bind.annotation.RestController
@RequestMapping("/examen")
@RequiredArgsConstructor
public class RestController {
    private final IService service;



    @PostMapping("/addBank")
    public Bank addBank(@RequestBody Bank bank){
        return service.ajouterBank(bank);
    }

    @PostMapping("/addCompteAffect/{agenceBank}")
    public Compte addCompteAffect(@RequestBody Compte compte ,@PathVariable String agenceBank){
        return service.ajouterCompteEtAffecterAAgence(compte,agenceBank);
    }

    @PostMapping("/addVirement")
    public String ajoutrVirement(@RequestBody Transaction transaction){
        return service.ajouterVirement(transaction);
    }


    @PostMapping("/addRetrait")
    public String ajouterRetrait(@RequestBody Transaction transaction){
        return service.ajouterRetrait(transaction);
    }

    @PostMapping("/addVersement")
    public String ajouterVersement(@RequestBody Transaction transaction){
        return service.ajouterVersement(transaction);
    }

    @GetMapping("/getTransactionByBank/{idBank}")
    public List<Transaction> getAllTransactionByBank(@PathVariable Long idBank)
    {
        return service.getAllTransactionByBankId(idBank);
    }

}
