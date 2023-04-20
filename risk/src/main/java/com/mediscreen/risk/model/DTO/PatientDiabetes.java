package com.mediscreen.risk.model.DTO;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class PatientDiabetes {
    String firstName;
    String surname;
    int age;
    String diabetesAssessments;

}
