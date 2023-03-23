package com.mediscreen.WebApp.model.DTO;

import com.mediscreen.WebApp.model.PatientHistoryBean;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class PatientHistoryListDTO {

    private Integer idPatient;
    private String firstName;
    private String surname;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateOfBirthday;
    private String gender;
    private List<PatientHistoryBean> noteList;
}
