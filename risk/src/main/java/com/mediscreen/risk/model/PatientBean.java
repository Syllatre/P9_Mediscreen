package com.mediscreen.risk.model;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class PatientBean {

    private Integer idPatient;
    private String firstName;
    private String surname;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateOfBirthday;
    private String gender;
    private String phoneNumber;
    private String address;

}
