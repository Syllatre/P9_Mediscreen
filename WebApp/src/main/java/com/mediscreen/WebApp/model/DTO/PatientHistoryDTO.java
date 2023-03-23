package com.mediscreen.WebApp.model.DTO;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.Valid;
import java.time.LocalDate;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class PatientHistoryDTO {
    private String id;

    private Integer idPatient;
    private String firstName;
    private String surname;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateOfBirthday;
    private String gender;
    private String phoneNumber;
    private String adresse;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate noteDate;
    @Valid
    private String noteText;
}
