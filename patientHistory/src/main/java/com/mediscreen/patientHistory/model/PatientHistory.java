package com.mediscreen.patientHistory.model;



import javax.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Document(collection = "patient_history")
@Getter
@Setter
@ToString
public class PatientHistory {

    @Id
    private String id;

    private Integer patId;

   @NotBlank
    private String note;

    private LocalDate date;

}
