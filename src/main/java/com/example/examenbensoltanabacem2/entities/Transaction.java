package com.example.examenbensoltanabacem2.entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@ToString
public class Transaction implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long idTransaction;
    double montant;
    @Enumerated(EnumType.STRING)
    TypeTransaction typeTransaction;
    LocalDate dateTransaction;

    @ManyToOne
    Compte compteExpediteur;

    @ManyToOne
    Compte compteDestinataire;

}
