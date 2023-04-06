package com.mediscreen.patient.model;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
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
    private Integer idPatient;

    @NotEmpty
    @Column(name = "prenom", nullable = false, length = 45)
    private String firstName;
    @NotEmpty
    @Column(name = "nom", nullable = false, length = 45)
    private String surname;
    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
//    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
//    @JsonDeserialize(using = LocalDateDeserializer.class)
    @Past(message = "You can't choose date after today")
    @Column(name = "date_naissance")
    private LocalDate dateOfBirthday;
    @Pattern(regexp = "[MF]", message = "Select M or F")
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
