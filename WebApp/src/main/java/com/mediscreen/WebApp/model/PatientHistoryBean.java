package com.mediscreen.WebApp.model;

import lombok.*;

import javax.validation.constraints.NotBlank;
import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class PatientHistoryBean {


    private String id;

    private Integer idPatient;

    @NotBlank
    private String note;

    private LocalDate date;
}
