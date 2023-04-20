package com.mediscreen.risk.service;

import com.mediscreen.risk.constant.Keyword;
import com.mediscreen.risk.model.DTO.PatientDiabetes;
import com.mediscreen.risk.model.PatientBean;
import com.mediscreen.risk.model.PatientHistoryBean;
import com.mediscreen.risk.proxy.MicroservicePatientHistoryProxy;
import com.mediscreen.risk.proxy.MicroservicePatientProxy;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class RiskService {
    public MicroservicePatientProxy microservicePatientProxy;
    public MicroservicePatientHistoryProxy microservicePatientHistoryProxy;

    public int countNoteByKeyword(List<PatientHistoryBean> notes) {
        return notes.stream()
                .map(note -> note.getNote().toLowerCase())
                .flatMap(recommendation -> Keyword.KEYWORD_LIST.stream()
                        .filter(keyword -> recommendation.contains(keyword)))
                .mapToInt(keyword -> 1)
                .sum();
    }

    public int calculateAge(LocalDate dateOfBirthday) {
        return Period.between(dateOfBirthday, LocalDate.now()).getYears();
    }

    public String getRisk(PatientBean patientBean) {
        List<PatientHistoryBean> notePatient = microservicePatientHistoryProxy.getAllNotes().stream()
                .filter(note -> note.getPatId() == patientBean.getIdPatient())
                .collect(Collectors.toList());
        int numberOfTriggers = countNoteByKeyword(notePatient);
        int age = calculateAge(patientBean.getDateOfBirthday());
        String gender = patientBean.getGender();

        if (numberOfTriggers == 0) {
            return "None";
        } else if (numberOfTriggers == 2 && age > 30) {
            return "Borderline";
        } else {
            boolean isMale = gender.equalsIgnoreCase("M");
            boolean isYoungerThan30 = age < 30;

            if (isMale && isYoungerThan30 && numberOfTriggers >= 5) {
                return "Early onset";
            } else if (!isMale && isYoungerThan30 && numberOfTriggers >= 7) {
                return "Early onset";
            } else if (age > 30 && numberOfTriggers >= 8) {
                return "Early onset";
            } else if (isMale && isYoungerThan30 && numberOfTriggers >= 3) {
                return "In Danger";
            } else if (!isMale && isYoungerThan30 && numberOfTriggers >= 4) {
                return "In Danger";
            } else if (age > 30 && numberOfTriggers >= 6) {
                return "In Danger";
            }
        }
        return "None";
    }

    public PatientDiabetes getPatientDiabetes(Integer ipPatient){
        PatientBean patientBean = microservicePatientProxy.getPatientById(ipPatient);
        PatientDiabetes patientDiabetes = new PatientDiabetes();
        patientDiabetes.setDiabetesAssessments(getRisk(patientBean));
        patientDiabetes.setAge(calculateAge(patientBean.getDateOfBirthday()));
        patientDiabetes.setSurname(patientBean.getSurname());
        patientDiabetes.setFirstName(patientBean.getFirstName());
        return patientDiabetes;
    }

}
