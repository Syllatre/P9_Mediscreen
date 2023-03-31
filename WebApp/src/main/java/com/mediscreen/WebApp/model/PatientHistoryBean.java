package com.mediscreen.WebApp.model;

import lombok.*;
import org.springframework.data.annotation.Id;

import javax.validation.constraints.NotBlank;
import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class PatientHistoryBean {

    @Id
    private String id;

    private Integer idPatient;

    @NotBlank
    private String note;

    private LocalDate date;
}
