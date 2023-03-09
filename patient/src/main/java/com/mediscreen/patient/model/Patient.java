package com.mediscreen.patient.model;


import lombok.*;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
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
    @Column(name = "prenom", nullable = false, length = 45)
    private String prenom;
    @NotEmpty
    @Column(name = "nom", nullable = false, length = 45)
    private String nom;
    @NotNull
    @Temporal(TemporalType.DATE)
    @Column(name = "date_naissance")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dateNaissance;
    @NotEmpty
    @Column(name = "genre", nullable = false, length = 1)
    private String genre;
    @Column(name = "numero_telephone", length = 20)
    private String numeroTelephone;
    @Column(name = "adresse", length = 250)
    private String adresse;


}
