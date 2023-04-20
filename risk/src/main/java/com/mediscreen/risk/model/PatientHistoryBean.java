package com.mediscreen.risk.model;

import lombok.*;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class PatientHistoryBean {


    private String id;
    private Integer patId;
    private String note;
    private LocalDate date;
}
