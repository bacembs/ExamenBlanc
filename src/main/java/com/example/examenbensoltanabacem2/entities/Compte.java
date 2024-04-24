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
public class Compte implements Serializable {
    //Serializable assure l integrite des donnees
    //identity
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long idCompte;
    Long code;
    @Enumerated(EnumType.STRING)
    TypeCompte typeCompteC;
    double solde;

}
