package com.mediscreen.patient.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;


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
    @JsonIgnore
    private Long idPatient;

    @NotEmpty
    @Column(name = "prenom", nullable = false, length = 45)
    private String firstName;
    @NotEmpty
    @Column(name = "nom", nullable = false, length = 45)
    private String surname;
    @NotNull
    @Column(name = "date_naissance")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateOfBirthday;
    @NotEmpty
    @Column(name = "genre", nullable = false, length = 1)
    private String gender;
    @Column(name = "numero_telephone", length = 20)
    private String phoneNumber;
    @Column(name = "adresse", length = 250)
    private String address;

    public Patient(String firstName, String surname, LocalDate dateOfBirthday, String gender, String phoneNumber, String address) {
        this.firstName = firstName;
        this.surname = surname;
        this.dateOfBirthday = dateOfBirthday;
        this.gender = gender;
        this.phoneNumber = phoneNumber;
        this.address = address;
    }
}
