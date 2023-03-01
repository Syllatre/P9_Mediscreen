package com.mediscreen.patient.model;


import lombok.*;
import org.hibernate.annotations.DynamicUpdate;


import javax.persistence.*;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;

import java.util.Date;


@Entity
@Table(name = "patient")
@DynamicUpdate
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idpatient", nullable = false)
    private Long idPatient;

    @NotEmpty
    @Column(name = "patient",nullable = false,length = 45)
    private String prenom;
    @NotEmpty
    @Column(name = "nom",nullable = false,length = 45)
    private String nom;
    @NotEmpty
    @Column(name = "date_naissance",nullable = false)
    private Date dateNaissance;
    @NotEmpty
    @Column(name = "genre",nullable = false,length = 1)
    private String genre;
    @NotEmpty
    @Column(name = "nummero_telephone",nullable = false,length = 10)
    private String numeroTelephone;
    @NotEmpty
    @Column(name = "adresse",nullable = false,length = 250)
    private String adresse;


}
