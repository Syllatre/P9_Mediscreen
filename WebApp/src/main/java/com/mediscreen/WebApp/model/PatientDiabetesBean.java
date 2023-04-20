package com.mediscreen.WebApp.model;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class PatientDiabetesBean {
    String firstName;
    String surname;
    int age;
    String diabetesAssessments;
}
